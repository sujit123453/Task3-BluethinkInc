package com.bluethinkInc.card_service.dto;

import com.bluethinkInc.card_service.enums.CardStatus;
import com.bluethinkInc.card_service.enums.CardType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CardResponseDto {
    private Long id;
    private Long customerId;
    private String accountNumber;
    private String cardNumber;
    private CardType cardType;
    private CardStatus cardStatus;
    private LocalDate expiryDate;
    private LocalDateTime issuedAt;
}
