package com.bluethinkInc.transaction_service.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetailsResponseDto {
    private String accountNumber;
    private String accountType;
    private String accountStatus;
    private BigDecimal balance;
    private String currency;
}
