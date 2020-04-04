package com.lcarvalho.isaid.api.infrastructure.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDBConfigTest {

    private static final String AMAZON_DYNAMODB_ENDPOINT = "http://localhost:8080";
    private static final String AMAZON_ACCESS_KEY = "testAccessKey";
    private static final String AMAZON_SECRET_KEY = "testSecretKey";
    private static final String AMAZON_REGION = "us-east-1";
    private static final Boolean USE_AMAZON_IAM_ROLE = Boolean.TRUE;

    private DynamoDBConfig dynamoDBConfig;

    @Test
    public void testConfigDBClientBuilder() {

        // Given
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When
        AmazonDynamoDBClientBuilder actualBuilder = dynamoDBConfig.configDBClientBuilder();

        // Then
        assertTrue(actualBuilder.getCredentials() instanceof InstanceProfileCredentialsProvider);
        assertEquals(AMAZON_DYNAMODB_ENDPOINT, actualBuilder.getEndpoint().getServiceEndpoint());
        assertEquals(AMAZON_REGION, actualBuilder.getEndpoint().getSigningRegion());
    }

    @Test
    public void testConfigDBClientBuilderUsingAccessAndSecretKeys() {

        // Given
        Boolean useAmazonIAMRole = Boolean.FALSE;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_REGION, useAmazonIAMRole);

        // When
        AmazonDynamoDBClientBuilder actualBuilder = dynamoDBConfig.configDBClientBuilder();

        // Then
        assertTrue(actualBuilder.getCredentials() instanceof AWSStaticCredentialsProvider);
        assertEquals(AMAZON_DYNAMODB_ENDPOINT, actualBuilder.getEndpoint().getServiceEndpoint());
        assertEquals(AMAZON_REGION, actualBuilder.getEndpoint().getSigningRegion());
        assertTrue(actualBuilder.getCredentials().getCredentials() instanceof BasicAWSCredentials);
        assertEquals(AMAZON_ACCESS_KEY, actualBuilder.getCredentials().getCredentials().getAWSAccessKeyId());
        assertEquals(AMAZON_SECRET_KEY, actualBuilder.getCredentials().getCredentials().getAWSSecretKey());
    }

    @Test
    public void testConfigDBClientBuilderWithNullAccessKeyUsingIAMRole() {

        // Given
        String amazonAccessKey = null;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, amazonAccessKey, AMAZON_SECRET_KEY, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When
        AmazonDynamoDBClientBuilder actualBuilder = dynamoDBConfig.configDBClientBuilder();

        // Then
        assertTrue(actualBuilder.getCredentials() instanceof InstanceProfileCredentialsProvider);
        assertEquals(AMAZON_DYNAMODB_ENDPOINT, actualBuilder.getEndpoint().getServiceEndpoint());
        assertEquals(AMAZON_REGION, actualBuilder.getEndpoint().getSigningRegion());
    }

    @Test
    public void testConfigDBClientBuilderWithEmptyAccessKeyUsingIAMRole() {

        // Given
        String amazonAccessKey = "";
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, amazonAccessKey, AMAZON_SECRET_KEY, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When
        AmazonDynamoDBClientBuilder actualBuilder = dynamoDBConfig.configDBClientBuilder();

        // Then
        assertTrue(actualBuilder.getCredentials() instanceof InstanceProfileCredentialsProvider);
        assertEquals(AMAZON_DYNAMODB_ENDPOINT, actualBuilder.getEndpoint().getServiceEndpoint());
        assertEquals(AMAZON_REGION, actualBuilder.getEndpoint().getSigningRegion());
    }

    @Test
    public void testConfigDBClientBuilderWithNullSecretKeyUsingIAMRole() {

        // Given
        String amazonSecretKey = null;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, amazonSecretKey, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When
        AmazonDynamoDBClientBuilder actualBuilder = dynamoDBConfig.configDBClientBuilder();

        // Then
        assertTrue(actualBuilder.getCredentials() instanceof InstanceProfileCredentialsProvider);
        assertEquals(AMAZON_DYNAMODB_ENDPOINT, actualBuilder.getEndpoint().getServiceEndpoint());
        assertEquals(AMAZON_REGION, actualBuilder.getEndpoint().getSigningRegion());
    }

    @Test
    public void testConfigDBClientBuilderWithEmptySecretKeyUsingIAMRole() {

        // Given
        String amazonSecretKey = "";
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, amazonSecretKey, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When
        AmazonDynamoDBClientBuilder actualBuilder = dynamoDBConfig.configDBClientBuilder();

        // Then
        assertTrue(actualBuilder.getCredentials() instanceof InstanceProfileCredentialsProvider);
        assertEquals(AMAZON_DYNAMODB_ENDPOINT, actualBuilder.getEndpoint().getServiceEndpoint());
        assertEquals(AMAZON_REGION, actualBuilder.getEndpoint().getSigningRegion());
    }

    @Test
    public void testConfigDBClientBuilderWithNullEndpoint() {

        // Given
        String amazonDynamoDBEndpoint = null;
        dynamoDBConfig = new DynamoDBConfig(amazonDynamoDBEndpoint, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithEmptyEndpoint() {

        // Given
        String amazonDynamoDBEndpoint = "";
        dynamoDBConfig = new DynamoDBConfig(amazonDynamoDBEndpoint, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_REGION, USE_AMAZON_IAM_ROLE);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithNullAccessKeyNotUsingIAMRole() {

        // Given
        String amazonAccessKey = null;
        Boolean useIAMRole = Boolean.FALSE;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, amazonAccessKey, AMAZON_SECRET_KEY, AMAZON_REGION, useIAMRole);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithEmptyAccessKeyNotUsingIAMRole() {

        // Given
        String amazonAccessKey = "";
        Boolean useIAMRole = Boolean.FALSE;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, amazonAccessKey, AMAZON_SECRET_KEY, AMAZON_REGION, useIAMRole);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithNullSecretKeyNotUsingIAMRole() {

        // Given
        String amazonSecretKey = null;
        Boolean useIAMRole = Boolean.FALSE;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, amazonSecretKey, AMAZON_REGION, useIAMRole);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithEmptySecretKeyNotUsingIAMRole() {

        // Given
        String amazonSecretKey = "";
        Boolean useIAMRole = Boolean.FALSE;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, amazonSecretKey, AMAZON_REGION, useIAMRole);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithNullRegion() {

        // Given
        String amazonRegion = null;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, amazonRegion, USE_AMAZON_IAM_ROLE);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithEmptyRegion() {

        // Given
        String amazonRegion = "";
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, amazonRegion, USE_AMAZON_IAM_ROLE);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }

    @Test
    public void testConfigDBClientBuilderWithNullUseIAMRole() {

        // Given
        Boolean useIAMRole = null;
        dynamoDBConfig = new DynamoDBConfig(AMAZON_DYNAMODB_ENDPOINT, AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY, AMAZON_REGION, useIAMRole);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> dynamoDBConfig.configDBClientBuilder());
    }
}