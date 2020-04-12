package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.commons.HttpClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FollowerResourceStepDefs {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private FollowerRepository followerRepository;

    private ResponseEntity actualResponseEntity;

    @Given("the following followers exists:")
    public void theFollowingFollowersExists(final List<Follower> followers) {
        followerRepository.saveAll(followers);
    }

    @When("clients makes a POST request to the Follower resource with {string} uri and {string} in the body")
    public void postRequestToFollower(final String uri, final String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, Follower.class);
    }

    @When("clients makes a GET request to the Follower resource with {string} uri")
    public void getRequestToFollowedProphets(final String uri) {
        actualResponseEntity = httpClient.get(uri, Follower[].class);
    }

    @Then("a {int} http response will be returned by the Follower resource")
    public void assertHttpResponseCode(final Integer expectedHttpResponseCode) {
        assertEquals(expectedHttpResponseCode.intValue(), actualResponseEntity.getStatusCodeValue());
    }

    @Then("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the reponse body")
    public void assertResponseBody(final String verify, final String expectedFollowerCode, final String expectedProphetCode) {
        if (Boolean.valueOf(verify)) {
            assertEquals(expectedFollowerCode, ((Follower) actualResponseEntity.getBody()).getFollowerCode());
            assertEquals(expectedProphetCode, ((Follower) actualResponseEntity.getBody()).getProphetCode());
        }
    }

    @Then("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the database")
    public void assertDatabase(final String verify, final String expectedFollowerCode, final String expectedProphetCode) {
        if (Boolean.valueOf(verify)) {
            List<Follower> actualFollowers = followerRepository.findByFollowerCode(expectedFollowerCode);
            assertTrue(actualFollowers.contains(buildFollower(expectedFollowerCode, expectedProphetCode)));
        }
    }

    @Then("the following followers will be returned in the response body")
    public void assertResponseBody(final List<Follower> expectedFollowers) {
        List<Follower> actualFollowers = Arrays.asList((Follower[])actualResponseEntity.getBody());
        assertEquals(expectedFollowers.size(), actualFollowers.size());
        for (Follower expectedFollower : expectedFollowers) {
            assertTrue(actualFollowers.contains(expectedFollower));
        }
    }

    @Then("{word} a follower list with {int} elements should be returned in the response body")
    public void assertResponseBodyFollowerListSize(final String verifyResponseBody, final Integer expectedSize) {
        if (Boolean.valueOf(verifyResponseBody)) {
            List<Follower> actualFollowers = Arrays.asList((Follower[]) actualResponseEntity.getBody());
            assertEquals(expectedSize.intValue(), actualFollowers.size());
        }
    }

    private Follower buildFollower(final String followerCode, final String prophetCode) {
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.setFollowerCode(followerCode);
        return new Follower(prophetCode, followerRequest);
    }

    @DataTableType
    public List<Follower> convertToFollowerList(final DataTable table) {
        return table.asMaps().stream()
                .map(m -> new Follower(m.get("followerCode"), m.get("prophetCode")))
                .collect(Collectors.toList());
    }
}
