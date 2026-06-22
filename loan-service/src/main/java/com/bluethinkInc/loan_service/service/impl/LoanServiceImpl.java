package com.bluethinkInc.loan_service.service.impl;

import com.bluethinkInc.loan_service.config.AccountServiceClient;
import com.bluethinkInc.loan_service.config.CustomerServiceClient;
import com.bluethinkInc.loan_service.dto.*;
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

    public LoanServiceImpl(LoanRepository loanRepository,
                           AccountServiceClient accountServiceClient,
                           CustomerServiceClient customerServiceClient) {
        this.loanRepository = loanRepository;
        this.accountServiceClient = accountServiceClient;
        this.customerServiceClient = customerServiceClient;
    }

    @Override
    public LoanResponse applyLoan(LoanRequest request) {
        // Verify customer has an active account
        try {
            AccountResponseDto account = accountServiceClient.getAccountByCustomerId(request.getCustomerId());
            if (!"ACTIVE".equalsIgnoreCase(account.getAccountStatus())) {
                throw new IllegalStateException("Customer account is not active. Cannot apply for loan.");
            }
        } catch (FeignException.NotFound e) {
            throw new IllegalStateException("No account found for customer. Only registered account holders can apply for a loan.");
        }
        Loan loan = new Loan();
        loan.setCustomerId(request.getCustomerId());
        loan.setLoanType(request.getLoanType());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setRemainingAmount(request.getLoanAmount());
        loan.setTenureMonths(request.getTenureMonths());
        loan.setInterestRate(request.getInterestRate());
        loan.setEmiAmount(calculateEmi(request.getLoanAmount(), request.getInterestRate(), request.getTenureMonths()));
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setAppliedAt(LocalDateTime.now());
        return toResponse(loanRepository.save(loan));
    }

    @Override
    public LoanResponse getLoanById(Long loanId) {
        return toResponse(findLoan(loanId));
    }

    @Override
    public LoanResponse approveLoan(Long loanId, LoanApprovalRequest request) {
        Loan loan = findLoan(loanId);

        // 1. Check KYC
        CustomerResponseDto customer = customerServiceClient.getCustomerById(loan.getCustomerId());
        if (customer.getKycCompleted() == null || !customer.getKycCompleted()) {
            throw new IllegalStateException("Loan cannot be approved: customer KYC is not completed.");
        }

        // 2. Check account status is ACTIVE
        AccountResponseDto account = accountServiceClient.getAccountByCustomerId(loan.getCustomerId());
        if (!"ACTIVE".equalsIgnoreCase(account.getAccountStatus())) {
            throw new IllegalStateException("Loan cannot be approved: customer account is not ACTIVE. Current status: " + account.getAccountStatus());
        }

        // 3. Check existing active loans : max:2
        long activeLoans = loanRepository.countByCustomerIdAndLoanStatus(loan.getCustomerId(), LoanStatus.APPROVED);
        if (activeLoans >= 2) {
            throw new IllegalStateException("Loan cannot be approved: customer already has " + activeLoans + " active loans. Maximum allowed is 2.");
        }

        // 4. Minimum average balance check based on loan type
        BigDecimal minBalance = getMinimumBalance(loan.getLoanType());
        if (account.getBalance().compareTo(minBalance) < 0) {
            throw new IllegalStateException("Loan cannot be approved: insufficient account balance. "
                    + loan.getLoanType() + " requires minimum balance of ₹" + minBalance
                    + ". Current balance: ₹" + account.getBalance());
        }

        loan.setLoanStatus(request.getLoanStatus());
        loan.setRemarks(request.getRemarks());
        loan.setApprovedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());
        return toResponse(loanRepository.save(loan));
    }

    @Override
    public LoanResponse payEmi(EmiPaymentRequest request) {
        Loan loan = findLoan(request.getLoanId());
        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Loan is not approved for EMI payment");
        }
        BigDecimal remaining = loan.getRemainingAmount().subtract(request.getAmount());
        loan.setRemainingAmount(remaining.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : remaining);
        if (loan.getRemainingAmount().compareTo(BigDecimal.ZERO) == 0) {
            loan.setLoanStatus(LoanStatus.CLOSED);
        }
        loan.setUpdatedAt(LocalDateTime.now());
        return toResponse(loanRepository.save(loan));
    }

    private Loan findLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));
    }

    private BigDecimal getMinimumBalance(String loanType) {
        if (loanType == null) return BigDecimal.valueOf(10000);
        return switch (loanType.toUpperCase()) {
            case "HOME LOAN"    -> BigDecimal.valueOf(50000);
            case "CAR LOAN"     -> BigDecimal.valueOf(25000);
            default            -> BigDecimal.valueOf(10000); // Personal Loan and others
        };
    }

    // EMI = P * r * (1+r)^n / ((1+r)^n - 1)
    private BigDecimal calculateEmi(BigDecimal principal, BigDecimal annualRate, int tenureMonths) {
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        double pow = Math.pow(onePlusR.doubleValue(), tenureMonths);
        BigDecimal powBd = BigDecimal.valueOf(pow);
        return principal.multiply(monthlyRate).multiply(powBd)
                .divide(powBd.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
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
