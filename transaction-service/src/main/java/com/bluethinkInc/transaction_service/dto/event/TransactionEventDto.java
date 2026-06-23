package com.bluethinkInc.transaction_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public TransactionEventDto(String email, String s, String phone, String toAccountNumber, String transactionReference, String deposit, String success, double v, String s1) {
        this.customerEmail=email;
        this.customerName=s;
        this.phone=phone;
        this.toAccountNumber=toAccountNumber;
        this.transactionReference=transactionReference;
        this.transactionType=deposit;
        this.transactionStatus=success;
        this.amount=v;
        this.message=s1;
    }

}
