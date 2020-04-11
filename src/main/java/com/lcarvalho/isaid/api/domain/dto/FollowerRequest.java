package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.springframework.util.StringUtils;

import static com.lcarvalho.isaid.api.application.resource.util.ValidationUtils.*;

public class FollowerRequest {

    private static final String INVALID_FOLLOWER_CODE_MESSSAGE = "followerCode cannot be null or an empty string";

    private String followerCode;

    public FollowerRequest() {}

    public String getFollowerCode() {
        return followerCode;
    }

    public void setFollowerCode(String followerCode) {
        this.followerCode = followerCode;
    }

    public void validate() throws InvalidParameterException {

        if (StringUtils.isEmpty(followerCode)) {
            throw new InvalidParameterException(INVALID_FOLLOWER_CODE_MESSSAGE);
        }

        validateUUID(followerCode);
    }
}
