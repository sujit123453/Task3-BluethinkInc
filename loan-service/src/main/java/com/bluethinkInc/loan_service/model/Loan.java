package com.bluethinkInc.loan_service.model;

import com.bluethinkInc.loan_service.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String loanType;

    private BigDecimal loanAmount;

    private BigDecimal remainingAmount;

    private Integer tenureMonths;

    private BigDecimal emiAmount;

    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    private String remarks;

    private LocalDateTime appliedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime updatedAt;


}
