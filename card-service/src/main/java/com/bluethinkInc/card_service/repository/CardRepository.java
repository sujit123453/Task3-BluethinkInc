package com.bluethinkInc.card_service.repository;

import com.bluethinkInc.card_service.enums.CardType;
import com.bluethinkInc.card_service.model.Card;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumberAndCardType(String accountNumber, @NotNull(message = "Card type is required") CardType cardType);
}
