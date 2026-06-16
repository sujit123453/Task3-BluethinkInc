package com.bluethinkInc.account_service.service.impl;

import com.bluethinkInc.account_service.config.CustomerServiceClient;
import com.bluethinkInc.account_service.customeException.exceptions.CustomerIdNotFoundException;
import com.bluethinkInc.account_service.dto.AccountResponse;
import com.bluethinkInc.account_service.dto.AccountStatusDto;
import com.bluethinkInc.account_service.dto.BalanceResponseDto;
import com.bluethinkInc.account_service.dto.OpenAccountRequest;
import com.bluethinkInc.account_service.dto.feignClient.CustomerResponseDto;
import com.bluethinkInc.account_service.enums.AccountStatus;
import com.bluethinkInc.account_service.model.Account;
import com.bluethinkInc.account_service.repo.AccountRepo;
import com.bluethinkInc.account_service.service.AccountService;
import com.bluethinkInc.account_service.utils.AccountNumberGenerator;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final CustomerServiceClient customerServiceClient;
    private final AccountNumberGenerator accountNumberGenerator;

    public AccountServiceImpl(AccountRepo accountRepo, CustomerServiceClient customerServiceClient,
                              AccountNumberGenerator accountNumberGenerator) {
        this.accountRepo = accountRepo;
        this.customerServiceClient = customerServiceClient;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    public AccountResponse openAccountService(OpenAccountRequest request) {
        CustomerResponseDto customer;
        // Verify customer exists in customer-service
        try {
          customer = customerServiceClient.getCustomerById(request.getCustomerId());
        } catch (FeignException.NotFound e) {
            throw new CustomerIdNotFoundException(
                "Customer with id " + request.getCustomerId() + " is not registered.");
        }

        Account account = new Account();
        account.setCustomerId(request.getCustomerId());
        account.setAccountType(request.getAccountType());
        account.setAccountNumber(accountNumberGenerator.generateAccountNumber(request.getAccountType()));
        account.setBalance(request.getInitialDeposit());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setCurrency("INR");
        account.setKycVerified(customer.getKycCompleted());
        account.setAct(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setActivatedAt(LocalDateTime.now());

        Account saved = accountRepo.save(account);

        return new AccountResponse(
                saved.getAccountNumber(),
                saved.getAccountType(),
                saved.getAccountStatus(),
                saved.getBalance(),
                saved.getCurrency()
        );
    }

    @Override
    public AccountResponse getAccountDetailsService(String accountNumber) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerIdNotFoundException("Account not found: " + accountNumber));
        return new AccountResponse(
                account.getAccountNumber(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getBalance(),
                account.getCurrency()
        );
    }

    @Override
    public BalanceResponseDto getAccountBalanceService(String accountNumber) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerIdNotFoundException("Account not found: " + accountNumber));
        return new BalanceResponseDto(account.getAccountNumber(), account.getBalance());
    }

    @Override
    public AccountStatusDto updateAccountStatusService(String accountNumber, AccountStatus accountStatus) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerIdNotFoundException("Account not found: " + accountNumber));
        account.setAccountStatus(accountStatus);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepo.save(account);
        return new AccountStatusDto("Account status updated successfully", account.getAccountNumber(), accountStatus);
    }

    @Override
    public AccountResponse updateBalanceService(String accountNumber, BigDecimal newBalance) {
        Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerIdNotFoundException("Account not found: " + accountNumber));
        account.setBalance(newBalance);
        account.setUpdatedAt(LocalDateTime.now());
        Account saved = accountRepo.save(account);
        return new AccountResponse(
                saved.getAccountNumber(),
                saved.getAccountType(),
                saved.getAccountStatus(),
                saved.getBalance(),
                saved.getCurrency()
        );
    }
}
