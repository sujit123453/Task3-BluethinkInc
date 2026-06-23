package com.bluethinkInc.notification_service.service.sqsListener;

import com.bluethinkInc.notification_service.dto.LoanEventDto;
import com.bluethinkInc.notification_service.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsLoanListener {

    private final NotificationService notificationService;
    public SqsLoanListener(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @SqsListener("loan-events.fifo")
    public void receiveLoanEvent(LoanEventDto event){

        switch (event.getEventType()){

            case "LOAN_APPLIED":
                notificationService.handleLoanAppliedEvent(event);
                break;

            case "LOAN_APPROVED || LOAN_REJECTED":
                notificationService.handleLoanApprovedEvent(event);
                break;

            default:
                notificationService.handleEmiPaidEvent(event);
                break;
        }
    }
}
