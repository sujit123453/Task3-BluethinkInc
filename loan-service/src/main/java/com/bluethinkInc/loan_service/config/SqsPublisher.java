package com.bluethinkInc.loan_service.config;

import com.bluethinkInc.loan_service.dto.event.LoanEventDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsPublisher {

    private final SqsTemplate sqsTemplate;
    @Value("${aws.sqs.loan-queue-url}")
    private String queueUrl;

    public SqsPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void publishLoanEvent(LoanEventDto event) {
        sqsTemplate.send(queueUrl, event);
    }
}
