package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.commons.HttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FollowerResourceStepDefs {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private FollowerRepository followerRepository;

    private ResponseEntity actualResponseEntity;

    @When("clients makes a POST request to the Follower resource with {string} uri and {string} in the body")
    public void postRequestToFollower(final String uri, final String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, Follower.class);
    }

    @Then("a {int} http response will be returned by the Follower resource")
    public void assertHttpResponseCode(final Integer expectedHttpResponseCode) {
        assertEquals(expectedHttpResponseCode.intValue(), actualResponseEntity.getStatusCodeValue());
    }

    @And("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the reponse body")
    public void assertResponseBody(final String verify, final String expectedFollowerCode, final String expectedProphetCode) {
        if (Boolean.valueOf(verify)) {
            assertEquals(expectedFollowerCode, ((Follower) actualResponseEntity.getBody()).getFollowerCode());
            assertEquals(expectedProphetCode, ((Follower) actualResponseEntity.getBody()).getProphetCode());
        }
    }

    @And("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the database")
    public void assertDatabase(final String verify, final String expectedFollowerCode, final String expectedProphetCode) {
        if (Boolean.valueOf(verify)) {
            List<Follower> actualFollowers = followerRepository.findByFollowerCode(expectedFollowerCode);
            assertTrue(actualFollowers.contains(buildFollower(expectedFollowerCode, expectedProphetCode)));
        }
    }

    private Follower buildFollower(final String followerCode, final String prophetCode) {
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(followerCode);
        return new Follower(prophetCode, followerRequest);
    }
}
