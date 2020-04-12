package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.service.FollowerService;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowerResourceTest {

    private static final String PROPHET_LOGIN = "login";
    private static final String PROPHET_CODE = "66c0dc04-20ba-431a-bf32-61f58df44974";
    private static final String FOLLOWER_CODE = "7c3132fd-c723-4b2c-a921-d21a3791f7ff";

    @Mock
    private FollowerService followerService;

    @InjectMocks
    private FollowerResource followerResource;

    @Test
    public void testCreateFollower() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        FollowerRequest followerRequest = buildFollowerRequest(FOLLOWER_CODE);
        Follower expectedFollower = new Follower(prophet.getProphetCode(), followerRequest);
        when(followerService.createFollower(eq(prophet.getLogin()), eq(followerRequest))).thenReturn(expectedFollower);

        // When
        ResponseEntity actualResponseEntity = followerResource.createFollower(prophet.getLogin(), followerRequest);

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedFollower, HttpStatus.OK);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateFollowerWithInvalidLogin() {

        // Given
        Prophet prophet = buildProphet(null, PROPHET_CODE);
        FollowerRequest followerRequest = buildFollowerRequest(FOLLOWER_CODE);

        // When
        ResponseEntity actualResponseEntity = followerResource.createFollower(prophet.getLogin(), followerRequest);

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateFollowerWithInvalidFollowerRequest() {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        FollowerRequest followerRequest = buildFollowerRequest(null);

        // When
        ResponseEntity actualResponseEntity = followerResource.createFollower(prophet.getLogin(), followerRequest);

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateFollowerWithNonexistentLogin() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        FollowerRequest followerRequest = buildFollowerRequest(FOLLOWER_CODE);
        when(followerService.createFollower(eq(prophet.getLogin()), eq(followerRequest))).thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = followerResource.createFollower(prophet.getLogin(), followerRequest);

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testRetrieveFollowedProphets() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        List<Follower> expectedFollowers = buildFollowers(prophet, 5);
        when(followerService.getProphetsFollowedBy(eq(prophet.getLogin()))).thenReturn(expectedFollowers);

        // When
        ResponseEntity actualResponseEntity = followerResource.getFollowedProphets(prophet.getLogin());

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedFollowers, HttpStatus.OK);
        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
        assertEquals(((List<Follower>)expectedResponseEntity.getBody()).size(), ((List<Follower>)actualResponseEntity.getBody()).size());
        for (Follower expectedFollower : (List<Follower>)expectedResponseEntity.getBody()) {
            assertTrue(((List<Follower>)actualResponseEntity.getBody()).contains(expectedFollower));
        }
    }

    @Test
    public void testRetrieveEmptyFollowedProphets() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        List<Follower> expectedFollowers = new ArrayList<>();
        when(followerService.getProphetsFollowedBy(eq(prophet.getLogin()))).thenReturn(expectedFollowers);

        // When
        ResponseEntity actualResponseEntity = followerResource.getFollowedProphets(prophet.getLogin());

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedFollowers, HttpStatus.OK);
        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
        assertEquals(((List<Follower>)expectedResponseEntity.getBody()).size(), ((List<Follower>)actualResponseEntity.getBody()).size());
    }

    @Test
    public void testRetrieveFollowedProphetsWithInvalidLogin() {

        // Given
        Prophet prophet = buildProphet(null, PROPHET_CODE);

        // When
        ResponseEntity actualResponseEntity = followerResource.getFollowedProphets(prophet.getLogin());

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testRetrieveFollowedProphetsWithNonexistentLogin() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet(PROPHET_LOGIN, PROPHET_CODE);
        List<Follower> expectedFollowers = new ArrayList<>();
        when(followerService.getProphetsFollowedBy(eq(prophet.getLogin()))).thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = followerResource.getFollowedProphets(prophet.getLogin());

        // Then
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedFollowers, HttpStatus.NOT_FOUND);
        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
    }

    private Prophet buildProphet(final String prophetLogin, final String prophetCode) {
        return new Prophet(prophetLogin, prophetCode);
    }

    private FollowerRequest buildFollowerRequest(final String followerCode) {
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(followerCode);
        return followerRequest;
    }

    private List<Follower> buildFollowers(final Prophet prophet, final Integer size) {
        List<Follower> followers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            followers.add(new Follower(UUID.randomUUID().toString(), prophet.getProphetCode()));
        }
        return followers;
    }
}