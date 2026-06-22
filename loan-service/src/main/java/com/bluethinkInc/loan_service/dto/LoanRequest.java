package com.bluethinkInc.loan_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {
    private Long customerId;
    private String loanType;
    private BigDecimal loanAmount;
    private Integer tenureMonths;
    private BigDecimal interestRate;
}
