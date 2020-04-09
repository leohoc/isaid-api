package com.lcarvalho.isaid.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
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
        this.createTables(Prophet.class, Prophecy.class, Follower.class);
    }

    private void createTables(Class... classes) {

        ListTablesResult tables = amazonDynamoDB.listTables();

        for (Class clazz : classes) {

            CreateTableRequest tableRequest = buildCreateTableRequest(clazz);

            if (tables.getTableNames().contains(tableRequest.getTableName())) {
                LOGGER.info("m=createTable, tableName={}, status=tableAlreadyExists", tableRequest.getTableName());
                continue;
            }

            amazonDynamoDB.createTable(tableRequest);
            LOGGER.info("m=createTable, tableName={}, status=tableCreated", tableRequest.getTableName());
        }
    }

    private CreateTableRequest buildCreateTableRequest(Class clazz) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(clazz);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1000L, 1000L));
        return tableRequest;
    }
}
