package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowerServiceTest {

    private static final String PROPHET_LOGIN = "login";
    private static final String PROPHET_CODE = "66c0dc04-20ba-431a-bf32-61f58df44974";
    private static final String FOLLOWER_CODE = "7c3132fd-c723-4b2c-a921-d21a3791f7ff";

    @Mock
    private ProphetService prophetService;

    @Mock
    private FollowerRepository followerRepository;

    @InjectMocks
    private FollowerService followerService;

    @Test
    public void testCreateFollower() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        FollowerRequest followerRequest = buildFollowerRequest(FOLLOWER_CODE);
        Follower expectedFollower = new Follower(prophet.getProphetCode(), followerRequest);
        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenReturn(prophet);
        when(followerRepository.save(eq(expectedFollower))).thenReturn(expectedFollower);

        // When
        Follower actualFollower = followerService.createFollower(prophet.getLogin(), followerRequest);

        // Then
        assertEquals(expectedFollower, actualFollower);
    }

    @Test
    public void testCreateFollowerWithNonexistentProphet() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        FollowerRequest followerRequest = buildFollowerRequest(FOLLOWER_CODE);
        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenThrow(new ProphetNotFoundException());

        // When Then
        assertThrows(ProphetNotFoundException.class, () -> followerService.createFollower(prophet.getLogin(), followerRequest));
    }

    private FollowerRequest buildFollowerRequest(final String followerCode) {
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(followerCode);
        return followerRequest;
    }

    private Prophet buildProphet(final String login, final String prophetCode) {
        return new Prophet(login, prophetCode);
    }


}