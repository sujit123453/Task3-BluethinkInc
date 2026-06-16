package com.bluethinkInc.account_service.dto;

import com.bluethinkInc.account_service.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenAccountRequest {
    private Long customerId;
    private AccountType accountType;
    private BigDecimal initialDeposit;
}

