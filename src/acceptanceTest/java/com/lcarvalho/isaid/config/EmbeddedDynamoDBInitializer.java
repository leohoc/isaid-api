package com.lcarvalho.isaid.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmbeddedDynamoDBInitializer {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @BeforeTestClass
    public void setup() {
        this.createTableTest();
    }

    @AfterClass
    public void shutdown() {
        if (amazonDynamoDB != null) {
            amazonDynamoDB.shutdown();
        }
    }

    private void createTableTest() {

        String tableName = "Prophet";
        String hashKeyName = "login";
        CreateTableResult createTableResult = createTable(amazonDynamoDB, tableName, hashKeyName);

        TableDescription tableDescription = createTableResult.getTableDescription();
        LOGGER.info("tableName: " + tableDescription.getTableName());
        LOGGER.info("keySchema: " + tableDescription.getKeySchema().toString());
        LOGGER.info("attributeDefinitions: " + tableDescription.getAttributeDefinitions().toString());
        LOGGER.info("readCapacityUnits: " + tableDescription.getProvisionedThroughput().getReadCapacityUnits());
        LOGGER.info("writeCapacityUnits: " + tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
        LOGGER.info("tableStatus: " + tableDescription.getTableStatus());
        LOGGER.info("ARN: " + tableDescription.getTableArn());
    }

    private static CreateTableResult createTable(AmazonDynamoDB amazonDynamoDB, String tableName, String hashKeyName) {

        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition(hashKeyName, ScalarAttributeType.S));

        List<KeySchemaElement> keySchemaElements = new ArrayList<KeySchemaElement>();
        keySchemaElements.add(new KeySchemaElement(hashKeyName, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(keySchemaElements)
                .withProvisionedThroughput(provisionedthroughput);

        return amazonDynamoDB.createTable(request);
    }
}
