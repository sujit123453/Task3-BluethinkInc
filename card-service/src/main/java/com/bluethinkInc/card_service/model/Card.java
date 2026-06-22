package com.bluethinkInc.card_service.model;

import com.bluethinkInc.card_service.enums.CardStatus;
import com.bluethinkInc.card_service.enums.CardType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String accountNumber;

    @Column(unique = true)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    private String pin;

    private String cvv;

    private LocalDate expiryDate;

    private LocalDateTime issuedAt;

    private LocalDateTime updatedAt;
}
