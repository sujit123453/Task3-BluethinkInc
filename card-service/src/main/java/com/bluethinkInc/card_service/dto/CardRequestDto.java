package com.bluethinkInc.card_service.dto;

import com.bluethinkInc.card_service.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CardRequestDto {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Card type is required")
    private CardType cardType; // DEBIT or CREDIT
}
