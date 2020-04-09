package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

public class FollowerId {

    private String followerCode;
    private LocalDateTime eventTimestamp;

    public FollowerId() {}

    public FollowerId(String followerCode, LocalDateTime eventTimestamp) {
        this.followerCode = followerCode;
        this.eventTimestamp = eventTimestamp;
    }

    @DynamoDBHashKey
    public String getFollowerCode() {
        return followerCode;
    }

    public void setFollowerCode(String followerCode) {
        this.followerCode = followerCode;
    }

    @DynamoDBRangeKey
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
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
        FollowerId that = (FollowerId) o;
        return followerCode.equals(that.followerCode) &&
                eventTimestamp.equals(that.eventTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerCode, eventTimestamp);
    }
}
