package com.example.registration.configs;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().build();
    }
}
