package com.bluethinkInc.account_service.service;

import com.bluethinkInc.account_service.dto.AccountResponse;
import com.bluethinkInc.account_service.dto.AccountStatusDto;
import com.bluethinkInc.account_service.dto.BalanceResponseDto;
import com.bluethinkInc.account_service.dto.OpenAccountRequest;
import com.bluethinkInc.account_service.enums.AccountStatus;
import org.springframework.stereotype.Service;


public interface AccountService {
    AccountResponse openAccountService(OpenAccountRequest request);

    AccountResponse getAccountDetailsService(String accountNumber);

    BalanceResponseDto getAccountBalanceService(String accountNumber);

    AccountStatusDto updateAccountStatusService(String accountNumber, AccountStatus accountStatus);

    AccountResponse updateBalanceService(String accountNumber, java.math.BigDecimal newBalance);

    AccountResponse getAccountByCustomerIdService(Long customerId);
}
