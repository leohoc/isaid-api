package com.lcarvalho.isaid.api.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "Prophecy")
public class Prophecy {

    @Id
    private ProphecyId prophecyId;
    private String summary;
    private String description;

    public Prophecy() {}

    public Prophecy(ProphecyId prophecyId) {
        this.prophecyId = prophecyId;
    }

    public Prophecy(String prophetCode, String summary, String description) {
        this.prophecyId = new ProphecyId(prophetCode, LocalDateTime.now());
        this.summary = summary;
        this.description = description;
    }

    @DynamoDBHashKey(attributeName = "prophetCode")
    public String getProphetCode() {
        return prophecyId != null ? prophecyId.getProphetCode() : null;
    }

    public void setProphetCode(String prophetCode) {
        if (prophecyId == null) {
            prophecyId = new ProphecyId();
        }
        this.prophecyId.setProphetCode(prophetCode);
    }

    @DynamoDBRangeKey(attributeName = "prophecyTimestamp")
    @DynamoDBTypeConverted(converter = ProphecyId.LocalDateTimeConverter.class)
    public LocalDateTime getProphecyTimestamp() {
        return prophecyId != null ? prophecyId.getProphecyTimestamp() : null;
    }

    public void setProphecyTimestamp(LocalDateTime prophecyTimestamp) {
        if (prophecyId == null) {
            prophecyId = new ProphecyId();
        }
        this.prophecyId.setProphecyTimestamp(prophecyTimestamp);
    }

    @DynamoDBAttribute
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
