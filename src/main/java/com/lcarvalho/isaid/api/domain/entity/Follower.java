package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneId;

@DynamoDBTable(tableName = "Follower")
public class Follower {

    private static final ZoneId saoPauloZoneId = ZoneId.of("America/Sao_Paulo");

    @Id
    private FollowerId followerId;
    private String prophetCode;

    public Follower() {}

    @DynamoDBHashKey(attributeName = "followerCode")
    public String getFollowerCode() {
        return followerId != null ? followerId.getFollowerCode() : null;
    }

    public void setFollowerCode(String followerCode) {
        if (followerId == null) {
            followerId = new FollowerId();
        }
        this.followerId.setFollowerCode(followerCode);
    }

    @DynamoDBRangeKey(attributeName = "eventTimestamp")
    @DynamoDBTypeConverted(converter = FollowerId.LocalDateTimeConverter.class)
    public LocalDateTime getEventTimestamp() {
        return followerId != null ? followerId.getEventTimestamp() : null;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        if (followerId == null) {
            followerId = new FollowerId();
        }
        this.followerId.setEventTimestamp(eventTimestamp);
    }

    @DynamoDBAttribute
    public String getProphetCode() {
        return prophetCode;
    }

    public void setProphetCode(String prophetCode) {
        this.prophetCode = prophetCode;
    }
}
