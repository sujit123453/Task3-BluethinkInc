package com.bluethinkInc.notification_service.service;

import com.bluethinkInc.notification_service.dto.*;

import javax.smartcardio.Card;

public interface NotificationService {
    void handleAccountCreated(AccountEventDto event);

    void handleStatusChanged(AccountEventDto event);

    void handleBalanceUpdated(AccountEventDto event);

    void handleRegisterEvent(AuthEventDto event);

    void handleLoginOtpEvent(AuthEventDto event);

    void handleTransactionDepositEvent(TransactionEventDto event);

    void handleTransactionWithdrawEvent(TransactionEventDto event);

    void handleTransactionTransferEvent(TransactionEventDto event);

    void handleLoanAppliedEvent(LoanEventDto event);

    void handleLoanApprovedEvent(LoanEventDto event);

    void handleEmiPaidEvent(LoanEventDto event);

    void handleCardIssueEvent(CardEventDto event);

    void handleBlockCardEvent(CardEventDto event);

    void handleCardPinChangeEvent(CardEventDto event);
}
