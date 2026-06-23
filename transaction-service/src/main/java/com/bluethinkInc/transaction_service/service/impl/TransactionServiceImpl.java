package com.bluethinkInc.transaction_service.service.impl;

import com.bluethinkInc.transaction_service.config.AccountServiceClient;
import com.bluethinkInc.transaction_service.config.CustomerServiceClient;
import com.bluethinkInc.transaction_service.config.SqsPublisher;
import com.bluethinkInc.transaction_service.customeException.exceptions.AccountNotFoundException;
import com.bluethinkInc.transaction_service.customeException.exceptions.InsufficientBalanceException;
import com.bluethinkInc.transaction_service.dto.event.TransactionEventDto;
import com.bluethinkInc.transaction_service.dto.request.CreditRequestDto;
import com.bluethinkInc.transaction_service.dto.request.TransferRequestDto;
import com.bluethinkInc.transaction_service.dto.request.WithdrawRequestDto;
import com.bluethinkInc.transaction_service.dto.response.AccountDetailsResponseDto;
import com.bluethinkInc.transaction_service.dto.response.CustomerDetailsResponseDto;
import com.bluethinkInc.transaction_service.dto.response.TransactionResponseDto;
import com.bluethinkInc.transaction_service.enums.TransactionStatus;
import com.bluethinkInc.transaction_service.enums.TransactionType;
import com.bluethinkInc.transaction_service.model.Transaction;
import com.bluethinkInc.transaction_service.repository.TransactionRepo;
import com.bluethinkInc.transaction_service.service.TransactionService;
import com.bluethinkInc.transaction_service.utils.TransactionReferenceGenerator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountServiceClient accountServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final TransactionReferenceGenerator referenceGenerator;
    private final SqsPublisher sqsPublisher;

    public TransactionServiceImpl(TransactionRepo transactionRepo,
                                   AccountServiceClient accountServiceClient,
                                   CustomerServiceClient customerServiceClient,
                                   TransactionReferenceGenerator referenceGenerator,
                                   SqsPublisher sqsPublisher) {
        this.transactionRepo = transactionRepo;
        this.accountServiceClient = accountServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.referenceGenerator = referenceGenerator;
        this.sqsPublisher = sqsPublisher;
    }

    @Override
    public TransactionResponseDto depositAmountService(CreditRequestDto request) {

        AccountDetailsResponseDto account = fetchAccount(request.getToAccountNumber());
        if(!account.getAccountNumber().equals(request.getToAccountNumber())){
            throw new AccountNotFoundException("Invalid account number, please provide valid accountNumber");
        }

        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        accountServiceClient.updateBalance(request.getToAccountNumber(), newBalance);

        Transaction txn = buildTransaction(
                null, request.getToAccountNumber(),
                request.getAmount(), TransactionType.CREDIT, request.getPerformedBy(),
                request.getDescription()
        );
        TransactionResponseDto response = toResponse(transactionRepo.save(txn));

        CustomerDetailsResponseDto customer = customerServiceClient.getCustomerById(account.getCustomerId());
        sqsPublisher.publishTransactionEvent(
                TransactionEventDto.builder()
                        .eventType("DEPOSIT")
                        .customerEmail(customer.getEmail())
                        .customerName(customer.getFirstName() + " " + customer.getLastName())
                        .phone(customer.getPhone())
                        .fromAccountNumber(request.getToAccountNumber())
                        .transactionReference(response.getTransactionReference())
                        .transactionType("DEPOSIT")
                        .transactionStatus("SUCCESS")
                        .amount(request.getAmount().doubleValue())
                        .message("Your account has been credited with ₹" + request.getAmount())
                        .build()
        );
        return response;
    }

    @Override
    public TransactionResponseDto withdrawAmountService(WithdrawRequestDto request) {
        AccountDetailsResponseDto account = fetchAccount(request.getFromAccountNumber());

        enforceCustomerOwnership(account.getCustomerId(), "Access denied: you can only withdraw from your own account");

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance in account: " + request.getFromAccountNumber());
        }

        BigDecimal newBalance = account.getBalance().subtract(request.getAmount());
        accountServiceClient.updateBalance(request.getFromAccountNumber(), newBalance);

        Transaction txn = buildTransaction(
                request.getFromAccountNumber(), null,
                request.getAmount(), TransactionType.DEBIT, request.getPerformedBy(),
                request.getDescription()
        );
        TransactionResponseDto response = toResponse(transactionRepo.save(txn));

        CustomerDetailsResponseDto customer = customerServiceClient.getCustomerById(account.getCustomerId());
        sqsPublisher.publishTransactionEvent(
                TransactionEventDto.builder()
                        .eventType("WITHDRAW")
                        .customerEmail(customer.getEmail())
                        .customerName(customer.getFirstName() + " " + customer.getLastName())
                        .phone(customer.getPhone())
                        .toAccountNumber(request.getFromAccountNumber())
                        .transactionReference(response.getTransactionReference())
                        .transactionType("DEPOSIT")
                        .transactionStatus("SUCCESS")
                        .amount(request.getAmount().doubleValue())
                        .message("Your account has been credited with ₹" + request.getAmount())
                        .build()
        );
        return response;
    }

    @Override
    public TransactionResponseDto transferAmountService(TransferRequestDto request) {
        AccountDetailsResponseDto sender = fetchAccount(request.getFromAccountNumber());
        fetchAccount(request.getToAccountNumber());

        enforceCustomerOwnership(sender.getCustomerId(), "Access denied: you can only transfer from your own account");

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance in account: " + request.getFromAccountNumber());
        }

        accountServiceClient.updateBalance(request.getFromAccountNumber(),
                sender.getBalance().subtract(request.getAmount()));

        AccountDetailsResponseDto receiver = fetchAccount(request.getToAccountNumber());
        accountServiceClient.updateBalance(request.getToAccountNumber(),
                receiver.getBalance().add(request.getAmount()));

        Transaction txn = buildTransaction(
                request.getFromAccountNumber(), request.getToAccountNumber(),
                request.getAmount(), TransactionType.DEBIT, request.getPerformedBy(),
                request.getDescription()
        );
        TransactionResponseDto response = toResponse(transactionRepo.save(txn));

        CustomerDetailsResponseDto customer = customerServiceClient.getCustomerById(sender.getCustomerId());
        sqsPublisher.publishTransactionEvent(
                TransactionEventDto.builder()
                        .eventType("TRANSFER_MONEY")
                        .customerEmail(customer.getEmail())
                        .customerName(customer.getFirstName() + " " + customer.getLastName())
                        .phone(customer.getPhone())
                        .fromAccountNumber(request.getFromAccountNumber())
                        .toAccountNumber(request.getToAccountNumber())
                        .transactionReference(response.getTransactionReference())
                        .transactionType("DEPOSIT")
                        .transactionStatus("SUCCESS")
                        .amount(request.getAmount().doubleValue())
                        .message("Your account has been credited with ₹" + request.getAmount())
                        .build()
        );
        return response;
    }

    @Override
    public Object getTransactionHistoryService(String accountNumber) {
        List<Transaction> history = transactionRepo
                .findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber);
        return history.stream().map(this::toResponse).toList();
    }

    private void enforceCustomerOwnership(Long accountCustomerId, String errorMessage) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            String phone = (String) auth.getPrincipal();
            CustomerDetailsResponseDto customer = customerServiceClient.getCustomerByPhone(phone);
            if (!customer.getId().equals(accountCustomerId)) {
                throw new SecurityException(errorMessage);
            }
        }
    }

    private AccountDetailsResponseDto fetchAccount(String accountNumber) {
        try {
            return accountServiceClient.getAccountDetails(accountNumber);
        } catch (FeignException.NotFound e) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
    }

    private Transaction buildTransaction(String from, String to, BigDecimal amount,
                                          TransactionType type, String performedBy,
                                         String description) {
        Transaction txn = new Transaction();
        txn.setTransactionReference(referenceGenerator.generateTransactionReference());
        txn.setFromAccountNumber(from);
        txn.setToAccountNumber(to);
        txn.setAmount(amount);
        txn.setTransactionType(type);
        txn.setTransactionStatus(TransactionStatus.SUCCESS);
        txn.setPerformedBy(performedBy);
        txn.setDescription(description);
        txn.setTransactionDate(LocalDateTime.now());
        txn.setCreatedAt(LocalDateTime.now());
        return txn;
    }

    private TransactionResponseDto toResponse(Transaction txn) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setTransactionId(txn.getTransactionId());
        dto.setTransactionReference(txn.getTransactionReference());
        dto.setFromAccountNumber(txn.getFromAccountNumber());
        dto.setToAccountNumber(txn.getToAccountNumber());
        dto.setAmount(txn.getAmount().toPlainString());
        dto.setTransactionType(txn.getTransactionType());
        dto.setTransactionStatus(txn.getTransactionStatus());
        dto.setPerformedBy(txn.getPerformedBy());
        dto.setDescription(txn.getDescription());
        dto.setTransactionDate(txn.getTransactionDate().toString());
        return dto;
    }
}
