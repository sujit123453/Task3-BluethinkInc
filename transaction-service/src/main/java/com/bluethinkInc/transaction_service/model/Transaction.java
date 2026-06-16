package com.bluethinkInc.transaction_service.model;

import com.bluethinkInc.transaction_service.enums.TransactionStatus;
import com.bluethinkInc.transaction_service.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String transactionReference;

    private String fromAccountNumber;

    private String toAccountNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String description;

    private LocalDateTime transactionDate;

    private String performedBy;

    private LocalDateTime createdAt;
}
