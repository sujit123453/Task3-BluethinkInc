package com.bluethinkInc.loan_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmiPaymentRequest {
    private Long loanId;
    private BigDecimal amount;
}
