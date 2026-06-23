package com.bluethinkInc.account_service.config;

import com.bluethinkInc.account_service.dto.event.AccountEventDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqsPublisher {
    private final SqsTemplate sqsTemplate;

    @Value("${aws.sqs.account-queue-url}")
    private String queueUrl;

    public SqsPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void publicAccountEvent(AccountEventDto event) {
        sqsTemplate.send(queueUrl, event);
    }
}
