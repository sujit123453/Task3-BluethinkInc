package com.bluethinkInc.loan_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanEventDto {

    private String eventType;

    private Long loanId;

    private Long customerId;

    private String customerName;

    private String phone;

    private String email;

    private Double amount;

    private Integer tenureMonths;

    private Double interestRate;

    private String loanStatus;

    private String message;
}