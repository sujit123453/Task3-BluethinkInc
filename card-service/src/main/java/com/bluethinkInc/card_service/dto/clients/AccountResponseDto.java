package com.bluethinkInc.card_service.dto.clients;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AccountResponseDto {
    private String accountNumber;
    private String accountType;
    private String accountStatus;
    private Long customerId;
    private BigDecimal balance;
}
