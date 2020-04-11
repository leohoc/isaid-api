package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@DynamoDBTable(tableName = "Follower")
public class Follower {

    private static final ZoneId SAO_PAULO_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    @Id
    private FollowerId followerId;
    private String prophetCode;

    public Follower() {}

    public Follower(final String prophetCode, final FollowerRequest followerRequest) {
        this.followerId = new FollowerId(followerRequest.getFollowerCode(), LocalDateTime.now(SAO_PAULO_ZONE_ID));
        this.prophetCode = prophetCode;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        return Objects.equals(followerId.getFollowerCode(), follower.followerId.getFollowerCode()) &&
                Objects.equals(prophetCode, follower.prophetCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId.getFollowerCode(), prophetCode);
    }
}
