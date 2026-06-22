package com.bluethinkInc.card_service.dto;

import lombok.Data;

@Data
public class ChangePinRequestDto {
    private Long cardId;
    private String accountNumber;
    private String oldPin;
    private String newPin;
}
