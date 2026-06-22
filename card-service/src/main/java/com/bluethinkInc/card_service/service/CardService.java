package com.bluethinkInc.card_service.service;

import com.bluethinkInc.card_service.dto.CardRequestDto;
import com.bluethinkInc.card_service.dto.CardResponseDto;
import com.bluethinkInc.card_service.dto.ChangePinRequestDto;
import com.bluethinkInc.card_service.dto.IssueCardRequest;

public interface CardService {
    CardResponseDto issueCard(CardRequestDto request);
    CardResponseDto getCardById(Long cardId);
    void blockCard(Long cardId);
    void changePin(ChangePinRequestDto request);
}
