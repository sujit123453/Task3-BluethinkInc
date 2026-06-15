package com.bluethinkInc.account_service.dto;

import com.bluethinkInc.account_service.enums.AccountType;

import java.math.BigDecimal;

public class OpenAccountRequest {
    private Long customerId;
    private AccountType accountType;
    private BigDecimal initialDeposit;
}
