package com.bluethinkInc.card_service.dto;

import com.bluethinkInc.card_service.enums.CardType;
import lombok.Data;

@Data
public class IssueCardRequest {
    private Long customerId;
    private String accountNumber;
    private CardType cardType;
}
