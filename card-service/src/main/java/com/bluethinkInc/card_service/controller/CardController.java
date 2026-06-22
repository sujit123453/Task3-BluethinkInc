package com.bluethinkInc.card_service.controller;

import com.bluethinkInc.card_service.dto.*;
import com.bluethinkInc.card_service.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    // Issue new card
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNTANT','MANAGER')")
    @PostMapping("/issue")
    public ResponseEntity<ApiResponse<CardResponseDto>> issueCard(
            @RequestBody CardRequestDto request) {

        CardResponseDto response = cardService.issueCard(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Card issued successfully",
                        response,
                        HttpStatus.CREATED.value()
                ));
    }

    // Get card details
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNTANT','MANAGER')")
    @GetMapping("/{cardId}")
    public ResponseEntity<ApiResponse<CardResponseDto>> getCardById(
            @PathVariable Long cardId) {

        CardResponseDto response = cardService.getCardById(cardId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Card details fetched successfully",
                        response,
                        HttpStatus.OK.value()
                )
        );
    }

    // Block card
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNTANT','MANAGER')")
    @PutMapping("/block/{cardId}")
    public ResponseEntity<ApiResponse<String>> blockCard(
            @PathVariable Long cardId) {

        cardService.blockCard(cardId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Card blocked successfully",
                        "Card ID " + cardId + " blocked",
                        HttpStatus.OK.value()
                )
        );
    }

    // Change PIN
    @PreAuthorize("hasAnyRole('ADMIN','ACCOUNTANT','MANAGER','CUSTOMER')")
    @PutMapping("/change-pin")
    public ResponseEntity<ApiResponse<String>> changePin(
            @RequestBody ChangePinRequestDto request) {

        cardService.changePin(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "PIN changed successfully",
                        null,
                        HttpStatus.OK.value()
                )
        );
    }
}