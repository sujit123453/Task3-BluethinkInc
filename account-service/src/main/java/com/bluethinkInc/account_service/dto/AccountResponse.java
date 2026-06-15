package com.bluethinkInc.account_service.dto;

import com.bluethinkInc.account_service.enums.AccountStatus;
import com.bluethinkInc.account_service.enums.AccountType;

import java.math.BigDecimal;

public class AccountResponse {
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private String curr;
}
