package com.lcarvalho.isaid.api.application.config;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.google.common.annotations.VisibleForTesting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;

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

    public DynamoDBConfig() {}

    @VisibleForTesting
    public DynamoDBConfig(String amazonDynamoDBEndpoint,
                          String amazonAWSAccessKey,
                          String amazonAWSSecretKey,
                          String amazonAWSRegion,
                          Boolean useAmazonIAMRole) {
        this.amazonDynamoDBEndpoint = amazonDynamoDBEndpoint;
        this.amazonAWSAccessKey = amazonAWSAccessKey;
        this.amazonAWSSecretKey = amazonAWSSecretKey;
        this.amazonAWSRegion = amazonAWSRegion;
        this.useAmazonIAMRole = useAmazonIAMRole;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {

        LOGGER.info("amazonDynamoDBEndpoint: " + amazonDynamoDBEndpoint);
        LOGGER.info("amazonAWSAccessKey: " + amazonAWSAccessKey);
        LOGGER.info("amazonAWSSecretKey: " + amazonAWSSecretKey);
        LOGGER.info("amazonAWSRegion: " + amazonAWSRegion);
        LOGGER.info("useAmazonIAMRole: " + useAmazonIAMRole);

        AmazonDynamoDBClientBuilder builder = configDBClientBuilder();
        return builder.build();
    }

    protected AmazonDynamoDBClientBuilder configDBClientBuilder() {

        validateProperties();

        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        builder.setEndpointConfiguration(amazonEndpointConfiguration());
        builder.withCredentials(amazonAWSCredentials());
        return builder;
    }

    private AwsClientBuilder.EndpointConfiguration amazonEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAWSRegion);
    }

    private AWSCredentialsProvider amazonAWSCredentials() {
        if (useAmazonIAMRole) {
            return InstanceProfileCredentialsProvider.getInstance();
        }
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }

    private void validateProperties() {
        if (StringUtils.isEmpty(amazonDynamoDBEndpoint) || StringUtils.isEmpty(amazonAWSRegion) || useAmazonIAMRole == null) {
            throw new InvalidParameterException();
        }
        if (!useAmazonIAMRole && (StringUtils.isEmpty(amazonAWSAccessKey) || StringUtils.isEmpty(amazonAWSSecretKey))) {
            throw new InvalidParameterException();
        }
    }
}
