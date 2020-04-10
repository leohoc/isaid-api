package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.domain.entity.Follower;

import java.time.LocalDateTime;
import java.util.Objects;

public class FollowerDTO {

    private String followerCode;
    private LocalDateTime eventTimestamp;
    private String prophetCode;

    public FollowerDTO() {}

    public FollowerDTO(Follower follower) {
        this.followerCode = follower.getFollowerCode();
        this.eventTimestamp = follower.getEventTimestamp();
        this.prophetCode = follower.getProphetCode();
    }

    public FollowerDTO(String followerCode, String prophetCode) {
        this.followerCode = followerCode;
        this.prophetCode = prophetCode;
    }

    public String getFollowerCode() {
        return followerCode;
    }

    public void setFollowerCode(String followerCode) {
        this.followerCode = followerCode;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

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
        FollowerDTO that = (FollowerDTO) o;
        return followerCode.equals(that.followerCode) &&
                prophetCode.equals(that.prophetCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerCode, prophetCode);
    }
}
