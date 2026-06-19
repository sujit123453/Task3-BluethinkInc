package com.bluethinkInc.notification_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEventDto {
    private String customerEmail;
    private String customerName;
    private String phone;
    private String accountNumber;
    private String transactionReference;
    private String transactionType;
    private String transactionStatus;
    private Double amount;
    private String message;
}
