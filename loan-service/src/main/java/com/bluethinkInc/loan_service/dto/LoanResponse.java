package com.bluethinkInc.loan_service.dto;

import com.bluethinkInc.loan_service.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanResponse {
    private Long id;
    private Long customerId;
    private String loanType;
    private BigDecimal loanAmount;
    private BigDecimal remainingAmount;
    private Integer tenureMonths;
    private BigDecimal emiAmount;
    private BigDecimal interestRate;
    private LoanStatus loanStatus;
    private String remarks;
    private LocalDateTime appliedAt;
    private LocalDateTime approvedAt;
}
