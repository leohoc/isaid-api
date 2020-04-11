package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FollowerRequestTest {

    private static final String FOLLOWER_CODE = "7c3132fd-c723-4b2c-a921-d21a3791f7ff";
    private static final String INVALID_FOLLOWER_CODE = "7c3132fdc7234b2ca921d21a3791f7ff";

    @Test
    public void testValidate() {

        // Given
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(FOLLOWER_CODE);

        // When Then
        assertDoesNotThrow(() -> followerRequest.validate());
    }

    @Test
    public void testValidateWithNullFollowerCode() {

        // Given
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(null);

        // When Then
        assertThrows(InvalidParameterException.class, () -> followerRequest.validate());
    }

    @Test
    public void testValidateWithEmptyFollowerCode() {

        // Given
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(null);

        // When Then
        assertThrows(InvalidParameterException.class, () -> followerRequest.validate());
    }

    @Test
    public void testValidateWithInvalidFollowerCode() {

        // Given
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(INVALID_FOLLOWER_CODE);

        // When Then
        assertThrows(IllegalArgumentException.class, () -> followerRequest.validate());
    }

}