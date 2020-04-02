package com.lcarvalho.isaid.api.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProphecyId {

    private String prophetCode;
    private LocalDateTime prophecyTimestamp;

    public ProphecyId() {}

    public ProphecyId(String prophetCode, LocalDateTime prophecyTimestamp) {
        this.prophetCode = prophetCode;
        this.prophecyTimestamp = prophecyTimestamp;
    }

    @DynamoDBHashKey
    public String getProphetCode() {
        return prophetCode;
    }

    public void setProphetCode(String prophetCode) {
        this.prophetCode = prophetCode;
    }

    @DynamoDBRangeKey
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getProphecyTimestamp() {
        return prophecyTimestamp;
    }

    public void setProphecyTimestamp(LocalDateTime prophecyTimestamp) {
        this.prophecyTimestamp = prophecyTimestamp;
    }

    static public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
        @Override
        public String convert(final LocalDateTime time) {
            return time.toString();
        }
        @Override
        public LocalDateTime unconvert(final String stringValue) {
            return LocalDateTime.parse(stringValue);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProphecyId that = (ProphecyId) o;
        return prophetCode.equals(that.prophetCode) &&
                prophecyTimestamp.equals(that.prophecyTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prophetCode, prophecyTimestamp);
    }
}
