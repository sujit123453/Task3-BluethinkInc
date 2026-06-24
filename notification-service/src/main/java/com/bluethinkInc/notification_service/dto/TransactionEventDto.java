package com.bluethinkInc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEventDto {
    private String eventType;
    private String customerEmail;
    private String customerName;
    private String phone;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String transactionReference;
    private String transactionType;
    private String transactionStatus;
    private Double amount;
    private String message;
}
