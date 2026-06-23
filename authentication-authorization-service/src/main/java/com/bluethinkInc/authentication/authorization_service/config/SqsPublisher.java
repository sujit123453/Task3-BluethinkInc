package com.bluethinkInc.authentication.authorization_service.config;

import com.bluethinkInc.authentication.authorization_service.dto.event.UserRegisterEventDto;
import com.bluethinkInc.authentication.authorization_service.dto.event.LoginOtpEventDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqsPublisher {
    private final SqsTemplate sqsTemplate;

    @Value("${aws.sqs.auth-queue-url}")
    private String queueUrl;

    public SqsPublisher(SqsTemplate sqsTemplate){
        this.sqsTemplate = sqsTemplate;
    }

    public void publishAccountEvent(LoginOtpEventDto event){
        sqsTemplate.send(queueUrl, event);
    }

    public void publishAccountRegisterEvent(UserRegisterEventDto event){
        sqsTemplate.send(queueUrl, event);
    }

}
