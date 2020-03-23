package com.lcarvalho.isaid.api.application.config;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.lcarvalho.isaid.api.infrastructure.persistence")
public class DynamoDBConfig {

    private static Logger LOGGER = LogManager.getLogger();

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

    @Value("${amazon.aws.iam-role}")
    private Boolean useAmazonIAMRole;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {

        LOGGER.info("amazonDynamoDBEndpoint: " + amazonDynamoDBEndpoint);
        LOGGER.info("amazonAWSAccessKey: " + amazonAWSAccessKey);
        LOGGER.info("amazonAWSSecretKey: " + amazonAWSSecretKey);
        LOGGER.info("amazonAWSRegion: " + amazonAWSRegion);
        LOGGER.info("useAmazonIAMRole: " + useAmazonIAMRole);

        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        builder.setEndpointConfiguration(amazonEndpointConfiguration());
        AmazonDynamoDB amazonDynamoDB = builder.withCredentials(amazonAWSCredentials()).build();
        return amazonDynamoDB;
    }

    private AwsClientBuilder.EndpointConfiguration amazonEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAWSRegion);
    }

    @Bean
    public AWSCredentialsProvider amazonAWSCredentials() {
        if (useAmazonIAMRole) {
            return InstanceProfileCredentialsProvider.getInstance();
        }
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }
}
