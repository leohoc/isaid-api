package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.application.resource.ProphetResource;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.service.ProphetService;
import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ProphetResourceStepDefs extends SpringAcceptanceTest {

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private ProphetResource prophetResource;

    private  ResponseEntity actualResponseEntity;

    @Given("that exists a registered prophet with {string} as login and {string} as prophetCode")
    public void createProphet(String login, String prophetCode) throws IOException, InvalidParameterException {
        prophetService.createProphet(login, prophetCode);
    }

    @When("clients makes a GET request to Prophet resource passing {string} as login")
    public void getProphet(String login) {
        actualResponseEntity = prophetResource.getProphet(login);
    }

    @When("clients makes a POST request with {string} as login")
    public void createProphet(String login) throws ProphetAlreadyExistsException {
        actualResponseEntity = prophetResource.createProphet(new Prophet(login, null));
    }

    @Then("a {int} http response with a body containing a prophet with {string} as login and {string} as code will be returned")
    public void assertGetProphetResponse(Integer expectedHttpStatus, String expectedLogin, String expectedProphetCode) {

        ResponseEntity expectedResponseEntity = buildResponseEntity(
                            HttpStatus.valueOf(expectedHttpStatus),
                            new Prophet(expectedLogin, expectedProphetCode));

        assertEquals(expectedResponseEntity.getStatusCode(), actualResponseEntity.getStatusCode());
        assertEquals(((Prophet)expectedResponseEntity.getBody()).getLogin(), ((Prophet)actualResponseEntity.getBody()).getLogin());
        assertEquals(((Prophet)expectedResponseEntity.getBody()).getProphetCode(), ((Prophet)actualResponseEntity.getBody()).getProphetCode());
    }

    @Then("a prophet with login equals to {string} should exist in the database")
    public void verifyProphetInDatabase(String expectedLogin) throws InvalidParameterException {
        Prophet expectedProphet = prophetService.retrieveProphetBy(expectedLogin);
        assertNotNull(expectedProphet);
        assertEquals(expectedLogin, expectedProphet.getLogin());
    }

    @Then("a {int} http response will be returned by the Prophet resource")
    public void assertHttpStatusResponse(Integer expectedHttpStatus) {
        assertEquals(HttpStatus.valueOf(expectedHttpStatus), actualResponseEntity.getStatusCode());
    }

    private ResponseEntity buildResponseEntity(HttpStatus httpStatus, Prophet prophet) {
        return new ResponseEntity(prophet, httpStatus);
    }
}
