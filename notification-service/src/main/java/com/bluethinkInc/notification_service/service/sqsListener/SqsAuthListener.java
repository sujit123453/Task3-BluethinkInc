package com.bluethinkInc.notification_service.service.sqsListener;

import com.bluethinkInc.notification_service.dto.AuthEventDto;
import com.bluethinkInc.notification_service.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsAuthListener {

    private final NotificationService notificationService;
    public SqsAuthListener(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @SqsListener("auth-events.fifo")
    public void receiveAuthEvent(AuthEventDto event){
        switch (event.getEventType()){

            case "ACCOUNT_REGISTERED":
                notificationService.handleRegisterEvent(event);
                break;

            case "LOGIN_OTP":
                notificationService.handleLoginOtpEvent(event);
                break;
        }
    }
}
