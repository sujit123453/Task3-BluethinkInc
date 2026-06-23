package com.bluethinkInc.notification_service.service.sqsListener;

import com.bluethinkInc.notification_service.dto.CardEventDto;
import com.bluethinkInc.notification_service.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsCardListener {
    private final NotificationService notificationService;
    public SqsCardListener(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @SqsListener("card-events.fifo")
    public void CardEvent(CardEventDto event){

        switch (event.getEventType()){

            case "CARD_ISSUED":
                notificationService.handleCardIssueEvent(event);
                break;

            case "BLOCKED":
                notificationService.handleBlockCardEvent(event);
                break;

            default:
                notificationService.handleCardPinChangeEvent(event);
                break;
        }
    }
}
