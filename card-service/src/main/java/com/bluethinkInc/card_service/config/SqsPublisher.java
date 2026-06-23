package com.bluethinkInc.card_service.config;

import com.bluethinkInc.card_service.dto.event.CardEventDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsPublisher {
    private final SqsTemplate sqsTemplate;

    public SqsPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Value("${aws.sqs.account-queue-url}")
    private String queueUrl;

    public void publicAccountEvent(CardEventDto event) {
        sqsTemplate.send(queueUrl, event);
    }
}
