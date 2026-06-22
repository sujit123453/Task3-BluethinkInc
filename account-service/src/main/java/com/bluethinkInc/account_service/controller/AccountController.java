package com.bluethinkInc.account_service.controller;

import com.bluethinkInc.account_service.dto.AccountResponse;
import com.bluethinkInc.account_service.dto.AccountStatusDto;
import com.bluethinkInc.account_service.dto.BalanceResponseDto;
import com.bluethinkInc.account_service.dto.OpenAccountRequest;
import com.bluethinkInc.account_service.enums.AccountStatus;
import com.bluethinkInc.account_service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNTANT','MANAGER')")
    @PostMapping("/open-account")
    public ResponseEntity<AccountResponse> openAccountController(@RequestBody OpenAccountRequest request) {
        AccountResponse response = accountService.openAccountService(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','ACCOUNTANT','MANAGER')")
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountDetailsController(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountDetailsService(accountNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','ACCOUNTANT','MANAGER')")
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BalanceResponseDto> getAccountBalanceController(@PathVariable String accountNumber) {
        BalanceResponseDto response = accountService.getAccountBalanceService(accountNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNTANT','MANAGER')")
    @PutMapping("/{accountNumber}/{accountStatus}")
    public ResponseEntity<AccountStatusDto> updateAccountStatusController(
            @PathVariable String accountNumber, @PathVariable AccountStatus accountStatus) {
        AccountStatusDto response = accountService.updateAccountStatusService(accountNumber, accountStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/internal/update-balance/{accountNumber}")
    public ResponseEntity<AccountResponse> updateBalanceInternal(
            @PathVariable String accountNumber, @RequestParam BigDecimal newBalance) {
        AccountResponse response = accountService.updateBalanceService(accountNumber, newBalance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/internal/customer/{customerId}")
    public ResponseEntity<AccountResponse> getAccountByCustomerIdInternal(@PathVariable Long customerId) {
        AccountResponse response = accountService.getAccountByCustomerIdService(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

