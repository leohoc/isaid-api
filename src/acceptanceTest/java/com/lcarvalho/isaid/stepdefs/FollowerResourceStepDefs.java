package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.dto.FollowerDTO;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.commons.HttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FollowerResourceStepDefs {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private FollowerRepository followerRepository;

    private ResponseEntity actualResponseEntity;

    @When("clients makes a POST request to the Follower resource with {string} uri and {string} in the body")
    public void postRequestToFollower(String uri, String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, FollowerDTO.class);
    }

    @Then("a {int} http response will be returned by the Follower resource")
    public void assertHttpResponseCode(Integer expectedHttpResponseCode) {
        assertEquals(expectedHttpResponseCode.intValue(), actualResponseEntity.getStatusCodeValue());
    }

    @And("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the reponse body")
    public void assertResponseBody(String verify, String expectedFollowerCode, String expectedProphetCode) {
        if (Boolean.valueOf(verify)) {
            assertEquals(expectedFollowerCode, ((FollowerDTO) actualResponseEntity.getBody()).getFollowerCode());
            assertEquals(expectedProphetCode, ((FollowerDTO) actualResponseEntity.getBody()).getProphetCode());
        }
    }

    @And("{word} a follower with followerCode equals to {string} and prophetCode equals to {string} in the database")
    public void assertDatabase(String verify, String expectedFollowerCode, String expectedProphetCode) {
        if (Boolean.valueOf(verify)) {
            List<FollowerDTO> actualFollowers = convertToFollowerDTOList(followerRepository.findByFollowerCode(expectedFollowerCode));
            assertTrue(actualFollowers.contains(new FollowerDTO(expectedFollowerCode, expectedProphetCode)));
        }
    }

    private List<FollowerDTO> convertToFollowerDTOList(List<Follower> followerList) {
        return followerList.stream().map(FollowerDTO::new).collect(Collectors.toList());
    }
}
