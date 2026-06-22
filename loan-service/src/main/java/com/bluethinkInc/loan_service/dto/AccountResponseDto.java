package com.bluethinkInc.loan_service.dto;

import lombok.Data;

@Data
public class AccountResponseDto {
    private String accountNumber;
    private String accountType;
    private String accountStatus;
    private Long customerId;
    private java.math.BigDecimal balance;
}
