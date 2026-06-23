package com.bluethinkInc.notification_service.service.sqsListener;

import com.bluethinkInc.notification_service.dto.AccountEventDto;
import com.bluethinkInc.notification_service.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsAccountListener {

    private final NotificationService notificationService;

    @SqsListener("account-events.fifo")
    public void receiveAccountEvent(AccountEventDto event) {

        switch (event.getEventType()) {

            case "ACCOUNT_CREATED":
                notificationService.handleAccountCreated(event);
                break;

            case "ACCOUNT_STATUS_CHANGED":
                notificationService.handleStatusChanged(event);
                break;

            case "ACCOUNT_BALANCE_UPDATED":
                notificationService.handleBalanceUpdated(event);
                break;
        }
    }
}

