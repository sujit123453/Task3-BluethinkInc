package com.bluethinkInc.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfig {

    @Bean
    public SqsMessageListenerContainerFactory<Object>
    defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {

        MappingJackson2MessageConverter payloadConverter =
                new MappingJackson2MessageConverter();
        payloadConverter.setObjectMapper(new ObjectMapper());
        payloadConverter.setStrictContentTypeMatch(false);

        SqsMessagingMessageConverter messageConverter =
                new SqsMessagingMessageConverter();

        messageConverter.setPayloadMessageConverter(payloadConverter);

        // Ignore JavaType header
        messageConverter.setPayloadTypeHeader("NONE");

        return SqsMessageListenerContainerFactory.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(options -> options.messageConverter(messageConverter))
                .build();
    }
}