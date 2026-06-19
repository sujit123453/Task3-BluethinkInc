package com.bluethinkInc.notification_service.service;

import com.bluethinkInc.notification_service.dto.event.TransactionEventDto;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqsNotificationListener {
//    private final SmsService smsService;
//    public SqsNotificationListener(SmsService smsService){
//        this.smsService = smsService;
//    }

    @SqsListener("transaction-events.fifo")
    public void receiveTransactionEvent(TransactionEventDto event) {
        System.out.println("Received notification for: " + event.getCustomerName()
                + " | Type: " + event.getTransactionType()
                + " | Amount: " + event.getAmount());

        String smsMessage = "Dear " + event.getCustomerName()
                + ", " + event.getMessage()
                + " Txn Ref: " + event.getTransactionReference()
                + " Status: " + event.getTransactionStatus();

//        smsService.sendSms(event.getPhone(),smsMessage);
        log.info("Sms request sent.");
    }
}
