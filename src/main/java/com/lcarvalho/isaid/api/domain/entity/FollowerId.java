package com.lcarvalho.isaid.api.domain.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

public class FollowerId {

    private String followerCode;
    private String prophetCode;

    public FollowerId() {}

    public FollowerId(String followerCode, String prophetCode) {
        this.followerCode = followerCode;
        this.prophetCode = prophetCode;
    }

    @DynamoDBHashKey
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "ProphetFollowersIndex")
    public String getFollowerCode() {
        return followerCode;
    }

    public void setFollowerCode(String followerCode) {
        this.followerCode = followerCode;
    }

    @DynamoDBRangeKey
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "ProphetFollowersIndex")
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
        FollowerId that = (FollowerId) o;
        return followerCode.equals(that.followerCode) &&
                prophetCode.equals(that.prophetCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerCode, prophetCode);
    }
}
