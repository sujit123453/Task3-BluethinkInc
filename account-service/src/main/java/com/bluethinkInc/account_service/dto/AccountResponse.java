package com.bluethinkInc.account_service.dto;

import com.bluethinkInc.account_service.enums.AccountStatus;
import com.bluethinkInc.account_service.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private String curr;
    private Long customerId;
}
