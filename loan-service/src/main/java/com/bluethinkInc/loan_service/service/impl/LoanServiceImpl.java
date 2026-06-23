package com.bluethinkInc.loan_service.service.impl;

import com.bluethinkInc.loan_service.config.AccountServiceClient;
import com.bluethinkInc.loan_service.config.CustomerServiceClient;
import com.bluethinkInc.loan_service.config.SqsPublisher;
import com.bluethinkInc.loan_service.dto.*;
import com.bluethinkInc.loan_service.dto.event.LoanEventDto;
import com.bluethinkInc.loan_service.exception.LoanNotFoundException;
import com.bluethinkInc.loan_service.model.Loan;
import com.bluethinkInc.loan_service.enums.LoanStatus;
import com.bluethinkInc.loan_service.repository.LoanRepository;
import com.bluethinkInc.loan_service.service.LoanService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final AccountServiceClient accountServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final SqsPublisher sqsPublisher;

    public LoanServiceImpl(LoanRepository loanRepository,
                           AccountServiceClient accountServiceClient,
                           CustomerServiceClient customerServiceClient,
                           SqsPublisher sqsPublisher) {
        this.loanRepository = loanRepository;
        this.accountServiceClient = accountServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.sqsPublisher = sqsPublisher;
    }

    @Override
    public LoanResponse applyLoan(LoanRequest request) {

        try {
            AccountResponseDto account =
                    accountServiceClient.getAccountByCustomerId(request.getCustomerId());

            if (!"ACTIVE".equalsIgnoreCase(account.getAccountStatus())) {
                throw new IllegalStateException(
                        "Customer account is not active. Cannot apply for loan.");
            }

        } catch (FeignException.NotFound e) {
            throw new IllegalStateException(
                    "No account found for customer. Only registered account holders can apply for a loan.");
        }

        Loan loan = new Loan();
        loan.setCustomerId(request.getCustomerId());
        loan.setPhone(request.getPhone());
        loan.setEmail(request.getEmail());
        loan.setLoanType(request.getLoanType());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setRemainingAmount(request.getLoanAmount());
        loan.setTenureMonths(request.getTenureMonths());
        loan.setInterestRate(request.getInterestRate());
        loan.setEmiAmount(
                calculateEmi(
                        request.getLoanAmount(),
                        request.getInterestRate(),
                        request.getTenureMonths()
                )
        );
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setAppliedAt(LocalDateTime.now());

        Loan savedLoan = loanRepository.save(loan);

        CustomerResponseDto customer =
                customerServiceClient.getCustomerById(savedLoan.getCustomerId());

        sqsPublisher.publishLoanEvent(
                buildLoanEvent(
                        "LOAN_APPLIED",
                        savedLoan,
                        customer,
                        savedLoan.getLoanAmount().doubleValue(),
                        "Your loan application has been submitted successfully."
                )
        );

        return toResponse(savedLoan);
    }

    @Override
    public LoanResponse getLoanById(Long loanId) {
        return toResponse(findLoan(loanId));
    }

    @Override
    public LoanResponse approveLoan(Long loanId, LoanApprovalRequest request) {

        Loan loan = findLoan(loanId);

        CustomerResponseDto customer =
                customerServiceClient.getCustomerById(loan.getCustomerId());

        if (customer.getKycCompleted() == null || !customer.getKycCompleted()) {
            throw new IllegalStateException(
                    "Loan cannot be approved: customer KYC is not completed.");
        }

        AccountResponseDto account =
                accountServiceClient.getAccountByCustomerId(loan.getCustomerId());

        if (!"ACTIVE".equalsIgnoreCase(account.getAccountStatus())) {
            throw new IllegalStateException(
                    "Loan cannot be approved: customer account is not ACTIVE.");
        }

        long activeLoans = loanRepository.countByCustomerIdAndLoanStatus(
                loan.getCustomerId(),
                LoanStatus.APPROVED
        );

        if (activeLoans >= 2) {
            throw new IllegalStateException(
                    "Customer already has maximum active loans.");
        }

        BigDecimal minBalance = getMinimumBalance(loan.getLoanType());

        if (account.getBalance().compareTo(minBalance) < 0) {
            throw new IllegalStateException(
                    "Insufficient balance for loan approval.");
        }

        loan.setLoanStatus(request.getLoanStatus());
        loan.setRemarks(request.getRemarks());
        loan.setApprovedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());

        Loan savedLoan = loanRepository.save(loan);

        String eventType;
        String message;

        if (savedLoan.getLoanStatus() == LoanStatus.APPROVED) {
            eventType = "LOAN_APPROVED";
            message = "Congratulations! Your loan has been approved.";
        } else {
            eventType = "LOAN_REJECTED";
            message = "Your loan application has been rejected.";
        }

        sqsPublisher.publishLoanEvent(
                buildLoanEvent(
                        eventType,
                        savedLoan,
                        customer,
                        savedLoan.getLoanAmount().doubleValue(),
                        message
                )
        );

        return toResponse(savedLoan);
    }

    @Override
    public LoanResponse payEmi(EmiPaymentRequest request) {

        Loan loan = findLoan(request.getLoanId());

        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException(
                    "Loan is not approved for EMI payment");
        }

        BigDecimal remaining =
                loan.getRemainingAmount().subtract(request.getAmount());

        loan.setRemainingAmount(
                remaining.compareTo(BigDecimal.ZERO) < 0
                        ? BigDecimal.ZERO
                        : remaining
        );

        if (loan.getRemainingAmount().compareTo(BigDecimal.ZERO) == 0) {
            loan.setLoanStatus(LoanStatus.CLOSED);
        }

        loan.setUpdatedAt(LocalDateTime.now());

        Loan savedLoan = loanRepository.save(loan);

        CustomerResponseDto customer =
                customerServiceClient.getCustomerById(savedLoan.getCustomerId());

        sqsPublisher.publishLoanEvent(
                buildLoanEvent(
                        "EMI_PAID",
                        savedLoan,
                        customer,
                        request.getAmount().doubleValue(),
                        "EMI payment received successfully."
                )
        );

        return toResponse(savedLoan);
    }

    private Loan findLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found with id: " + loanId));
    }

    private LoanEventDto buildLoanEvent(
            String eventType,
            Loan loan,
            CustomerResponseDto customer,
            Double amount,
            String message) {

        return LoanEventDto.builder()
                .eventType(eventType)
                .loanId(loan.getId())
                .customerId(customer.getId())
                .customerName(
                        customer.getFirstName() + " " + customer.getLastName())
                .phone(loan.getPhone())
                .email(loan.getEmail())
                .amount(amount)
                .tenureMonths(loan.getTenureMonths())
                .interestRate(loan.getInterestRate().doubleValue())
                .loanStatus(loan.getLoanStatus().name())
                .message(message)
                .build();
    }

    private BigDecimal getMinimumBalance(String loanType) {
        if (loanType == null) return BigDecimal.valueOf(10000);

        return switch (loanType.toUpperCase()) {
            case "HOME LOAN" -> BigDecimal.valueOf(50000);
            case "CAR LOAN" -> BigDecimal.valueOf(25000);
            default -> BigDecimal.valueOf(10000);
        };
    }

    private BigDecimal calculateEmi(
            BigDecimal principal,
            BigDecimal annualRate,
            int tenureMonths) {

        BigDecimal monthlyRate =
                annualRate.divide(BigDecimal.valueOf(1200), 10,
                        RoundingMode.HALF_UP);

        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);

        double pow = Math.pow(onePlusR.doubleValue(), tenureMonths);

        BigDecimal powBd = BigDecimal.valueOf(pow);

        return principal.multiply(monthlyRate)
                .multiply(powBd)
                .divide(powBd.subtract(BigDecimal.ONE), 2,
                        RoundingMode.HALF_UP);
    }

    private LoanResponse toResponse(Loan loan) {

        LoanResponse dto = new LoanResponse();

        dto.setId(loan.getId());
        dto.setCustomerId(loan.getCustomerId());
        dto.setLoanType(loan.getLoanType());
        dto.setLoanAmount(loan.getLoanAmount());
        dto.setRemainingAmount(loan.getRemainingAmount());
        dto.setTenureMonths(loan.getTenureMonths());
        dto.setEmiAmount(loan.getEmiAmount());
        dto.setInterestRate(loan.getInterestRate());
        dto.setLoanStatus(loan.getLoanStatus());
        dto.setRemarks(loan.getRemarks());
        dto.setAppliedAt(loan.getAppliedAt());
        dto.setApprovedAt(loan.getApprovedAt());

        return dto;
    }
}

