package com.bluethinkInc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardEventDto {
    private Long customerId;
    private String customerName;
    private String mobileNumber;
    private String email;

    private Long cardId;
    private String cardNumber;
    private String cardType;      // CREDIT / DEBIT
    private String status;        // ACTIVE / BLOCKED

    private String eventType;     // CARD_ISSUED, CARD_BLOCKED, PIN_CHANGED
    private String message;

    public CardEventDto(Long customerId, String name, String phone, String email, Long id, String cardNumber, String name1, String blocked, String s) {
        this.customerId=customerId;
        this.customerName=name;
        this.mobileNumber=phone;
        this.email=email;
        this.cardId=id;
        this.cardNumber=cardNumber;
        this.cardType=name1;
        this.status=blocked;
        this.message=s;
    }
}
