package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.ProphecyRequest;
import com.lcarvalho.isaid.api.domain.entity.converter.LocalDateTimeConverter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@DynamoDBTable(tableName = "Prophecy")
public class Prophecy {

    private static final ZoneId SAO_PAULO_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    @Id
    private ProphecyId prophecyId;
    private String summary;
    private String description;

    public Prophecy() {}

    public Prophecy(ProphecyId prophecyId) {
        this.prophecyId = prophecyId;
    }

    public Prophecy(final String prophectCode, final ProphecyRequest prophecyRequest) {
        this.prophecyId = new ProphecyId(prophectCode, LocalDateTime.now(SAO_PAULO_ZONE_ID));
        this.summary = prophecyRequest.getSummary();
        this.description = prophecyRequest.getDescription();
    }

    @VisibleForTesting
    public Prophecy(String prophetCode, LocalDateTime prophecyTimestamp, String summary, String description) {
        this.prophecyId = new ProphecyId(prophetCode, prophecyTimestamp);
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
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prophecy prophecy = (Prophecy) o;
        return prophecyId.getProphetCode().equals(prophecy.prophecyId.getProphetCode()) &&
                summary.equals(prophecy.summary) &&
                description.equals(prophecy.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prophecyId.getProphetCode(), summary, description);
    }
}
