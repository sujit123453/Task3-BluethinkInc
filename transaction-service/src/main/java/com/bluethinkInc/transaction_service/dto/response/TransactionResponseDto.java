package com.bluethinkInc.transaction_service.dto.response;

import com.bluethinkInc.transaction_service.enums.TransactionStatus;
import com.bluethinkInc.transaction_service.enums.TransactionType;
import lombok.Data;

@Data
public class TransactionResponseDto {
    private Long transactionId;
    private String transactionReference;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String performedBy;
    private String description;
    private String transactionDate;
}
