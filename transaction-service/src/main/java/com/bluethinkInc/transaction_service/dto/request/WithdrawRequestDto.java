package com.bluethinkInc.transaction_service.dto.request;

import com.bluethinkInc.transaction_service.enums.TransactionStatus;
import com.bluethinkInc.transaction_service.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WithdrawRequestDto {
    private String fromAccountNumber;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String performedBy;
    private String description;
    private LocalDateTime transactionDate;
}
