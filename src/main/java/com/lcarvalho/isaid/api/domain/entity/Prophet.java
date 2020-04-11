package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.ProphetRequest;

import java.util.UUID;

@DynamoDBTable(tableName = "Prophet")
public class Prophet {

    private String login;

    private String prophetCode;

    public Prophet() {}

    public Prophet(final ProphetRequest prophetRequest) {
        this.login = prophetRequest.getLogin();
        this.prophetCode = UUID.randomUUID().toString();
    }

    @VisibleForTesting
    public Prophet(final String login, final String prophetCode) {
        this.login = login;
        this.prophetCode = prophetCode;
    }

    @DynamoDBHashKey
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @DynamoDBAttribute
    public String getProphetCode() {
        return prophetCode;
    }

    public void setProphetCode(String prophetCode) {
        this.prophetCode = prophetCode;
    }

    @Override
    public String toString() {
        return "Prophet{" +
                "login='" + login + '\'' +
                ", prophetCode='" + prophetCode + '\'' +
                '}';
    }
}

