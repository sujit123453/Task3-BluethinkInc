package com.bluethinkInc.notification_service.service.sqsListener;

import com.bluethinkInc.notification_service.dto.TransactionEventDto;
import com.bluethinkInc.notification_service.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.Notification;

@Slf4j
@Service
public class SqsTransactionListener {
    private final NotificationService notificationService;
    public SqsTransactionListener(NotificationService notificationService){
        this.notificationService = notificationService;
    }
    @SqsListener("transaction-events.fifo")
    public void receiveTransactionEvent(TransactionEventDto event) {
       switch (event.getEventType()){

           case "DEPOSIT":
               notificationService.handleTransactionDepositEvent(event);
               break;

           case "WITHDRAW":
               notificationService.handleTransactionWithdrawEvent(event);
               break;

           default:
               notificationService.handleTransactionTransferEvent(event);
       }
    }

}
