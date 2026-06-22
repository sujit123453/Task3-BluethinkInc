package com.bluethinkInc.card_service.service.impl;

import com.bluethinkInc.card_service.config.AccountServiceClient;
import com.bluethinkInc.card_service.dto.CardRequestDto;
import com.bluethinkInc.card_service.dto.CardResponseDto;
import com.bluethinkInc.card_service.dto.ChangePinRequestDto;
import com.bluethinkInc.card_service.dto.clients.AccountResponseDto;
import com.bluethinkInc.card_service.enums.CardStatus;
import com.bluethinkInc.card_service.exception.CardNotFoundException;
import com.bluethinkInc.card_service.model.Card;
import com.bluethinkInc.card_service.repository.CardRepository;
import com.bluethinkInc.card_service.service.CardService;
import com.bluethinkInc.card_service.utils.GenerateRandomNum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountServiceClient accountServiceClient;
    private final GenerateRandomNum generateRandomNum;
    public CardServiceImpl(CardRepository cardRepository,
                           AccountServiceClient accountServiceClient,
                           GenerateRandomNum generateRandomNum){
        this.cardRepository = cardRepository;
        this.accountServiceClient = accountServiceClient;
        this.generateRandomNum = generateRandomNum;
    }
    @Override
    public CardResponseDto issueCard(CardRequestDto request) {

        // Verify account
        AccountResponseDto account =
                accountServiceClient.getAccountByCustomerId(request.getCustomerId());

        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        // Verify account status
        if (!"ACTIVE".equalsIgnoreCase(account.getAccountStatus())) {
            throw new IllegalArgumentException("Account is not active");
        }

        // Verify customer ownership
        if (!Objects.equals(account.getCustomerId(), request.getCustomerId())) {
            throw new IllegalArgumentException("Customer ID mismatch");
        }

        // Prevent duplicate card
        boolean exists = cardRepository.existsByAccountNumberAndCardType(
                account.getAccountNumber(),
                request.getCardType());

        if (exists) {
            throw new IllegalArgumentException(
                    request.getCardType() + " card already issued");
        }

        Card card = new Card();

        card.setCustomerId(request.getCustomerId());
        card.setAccountNumber(account.getAccountNumber());
        card.setCardType(request.getCardType());

        card.setCardNumber(generateRandomNum.generateCardNumber());
        card.setPin(generateRandomNum.generateCardPin());
        card.setCvv(generateRandomNum.generateCvv());

        card.setCardStatus(CardStatus.ACTIVE);

        card.setIssuedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());

        card.setExpiryDate(LocalDate.now().plusYears(5));

        Card savedCard = cardRepository.save(card);

        return mapToResponse(savedCard);
    }

    @Override
    public CardResponseDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()->
                        new CardNotFoundException("Card not found with this id"));
        return mapToResponse(card);
    }

    @Override
    public void blockCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()->
                        new CardNotFoundException("Card not found"));

        if(card.getCardStatus() == CardStatus.BLOCKED){
            throw new IllegalArgumentException("Card already blocked");
        }
        card.setCardStatus(CardStatus.BLOCKED);
        card.setUpdatedAt(LocalDateTime.now());
        cardRepository.save(card);
    }

    @Override
    public void changePin(ChangePinRequestDto request) {
        Long cardId = request.getCardId();
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()->
                        new CardNotFoundException("Card not found with this id: " + cardId));

        if (!card.getPin().equals(request.getOldPin())) {
            throw new IllegalArgumentException("Invalid old PIN");
        }

        if (request.getOldPin().equals(request.getNewPin())) {
            throw new IllegalArgumentException(
                    "New PIN cannot be same as old PIN");
        }
        if(!request.getAccountNumber().equals(card.getAccountNumber())){
            throw new IllegalArgumentException("Account number mismatch! please check your accountNumber..");
        }
        card.setPin(request.getNewPin());
        card.setUpdatedAt(LocalDateTime.now());

        cardRepository.save(card);
    }

    private CardResponseDto mapToResponse(Card card) {

        CardResponseDto dto = new CardResponseDto();

        dto.setId(card.getId());
        dto.setCustomerId(card.getCustomerId());
        dto.setAccountNumber(card.getAccountNumber());
        dto.setCardNumber(card.getCardNumber());
        dto.setCardType(card.getCardType());
        dto.setCardStatus(card.getCardStatus());
        dto.setExpiryDate(card.getExpiryDate());
        dto.setIssuedAt(card.getIssuedAt());

        return dto;
    }
}
