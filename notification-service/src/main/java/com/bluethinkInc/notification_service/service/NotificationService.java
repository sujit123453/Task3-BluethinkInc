package com.bluethinkInc.notification_service.service;

import com.bluethinkInc.notification_service.dto.AccountEventDto;
import com.bluethinkInc.notification_service.dto.AuthEventDto;
import com.bluethinkInc.notification_service.dto.TransactionEventDto;

public interface NotificationService {
    void handleAccountCreated(AccountEventDto event);
    void handleStatusChanged(AccountEventDto event);
    void handleBalanceUpdated(AccountEventDto event);
    void handleRegisterEvent(AuthEventDto event);
    void handleLoginOtpEvent(AuthEventDto event);

    void handleTransactionDepositEvent(TransactionEventDto event);

    void handleTransactionWithdrawEvent(TransactionEventDto event);

    void handleTransactionTransferEvent(TransactionEventDto event);
}
