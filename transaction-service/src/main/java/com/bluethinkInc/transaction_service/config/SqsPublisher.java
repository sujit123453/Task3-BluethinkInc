package com.bluethinkInc.transaction_service.config;

import com.bluethinkInc.transaction_service.dto.event.TransactionEventDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqsPublisher {

    private final SqsTemplate sqsTemplate;

    @Value("${spring.cloud.aws.sqs.queue-url}")
    private String queueUrl;

    public SqsPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void publishTransactionEvent(TransactionEventDto event) {
        sqsTemplate.send(queueUrl, event);
    }
}
