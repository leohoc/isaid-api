package com.lcarvalho.isaid.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@Component
public class EmbeddedDynamoDBInitializer {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @BeforeTestClass
    public void setup() {
        this.createProphetTable();
    }

    private void createProphetTable() {

        ListTablesResult tables = amazonDynamoDB.listTables();
        CreateTableRequest prophetTableRequest = buildCreateTableRequest(Prophet.class);

        if (tables.getTableNames().contains(prophetTableRequest.getTableName())) {
            LOGGER.info("m=createTable, status=tableAlreadyExists");
            return;
        }

        amazonDynamoDB.createTable(prophetTableRequest);
        LOGGER.info("m=createTable, status=tableCreated");
    }

    private CreateTableRequest buildCreateTableRequest(Class clazz) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(clazz);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1000L, 1000L));
        return tableRequest;
    }
}
