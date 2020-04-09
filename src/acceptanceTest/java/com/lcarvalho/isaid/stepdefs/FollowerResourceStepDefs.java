package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.commons.HttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class FollowerResourceStepDefs {

    @Autowired
    private HttpClient httpClient;

    private ResponseEntity actualResponseEntity;

    @When("clients makes a POST request to the Follower resource with {string} uri and {string} in the body")
    public void postRequestToFollower(String uri, String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, Follower.class);
    }

    @Then("a {int} http response will be returned by the Follower resource")
    public void assertHttpResponseCode(Integer httpResponseCode) {

    }

    @And("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the database")
    public void assertDatabase(String verifyDatabase, String expectedFollowerCode, String expectedProphetCode) {
        if (Boolean.valueOf(verifyDatabase)) {

        }
    }
}
