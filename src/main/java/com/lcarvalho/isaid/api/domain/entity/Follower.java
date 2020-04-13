package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.converter.LocalDateTimeConverter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@DynamoDBTable(tableName = "Follower")
public class Follower {

    private static final ZoneId SAO_PAULO_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    @Id
    private FollowerId followerId;
    private LocalDateTime eventTimestamp;

    public Follower() {}

    public Follower(final String prophetCode, final FollowerRequest followerRequest) {
        this.followerId = new FollowerId(followerRequest.getFollowerCode(), prophetCode);
        this.eventTimestamp = LocalDateTime.now(SAO_PAULO_ZONE_ID);
    }

    @VisibleForTesting
    public Follower(final String followerCode, final String prophetCode) {
        this.followerId = new FollowerId(followerCode, prophetCode);
        this.eventTimestamp = LocalDateTime.now(SAO_PAULO_ZONE_ID);
    }

    @DynamoDBHashKey(attributeName = "followerCode")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "ProphetFollowersIndex")
    public String getFollowerCode() {
        return followerId != null ? followerId.getFollowerCode() : null;
    }

    public void setFollowerCode(String followerCode) {
        if (followerId == null) {
            followerId = new FollowerId();
        }
        this.followerId.setFollowerCode(followerCode);
    }

    @DynamoDBRangeKey(attributeName = "prophetCode")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "ProphetFollowersIndex")
    public String getProphetCode() {
        return followerId != null ? followerId.getProphetCode() : null;
    }

    public void setProphetCode(String prophetCode) {
        if (followerId == null) {
            followerId = new FollowerId();
        }
        this.followerId.setProphetCode(prophetCode);
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        return followerId.equals(follower.followerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId);
    }
}
