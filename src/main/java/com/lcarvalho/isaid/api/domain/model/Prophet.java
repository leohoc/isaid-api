package com.lcarvalho.isaid.api.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Prophet")
public class Prophet {

    private String login;

    private String prophetCode;

    public Prophet() {}

    public Prophet(String login, String prophetCode) {
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

