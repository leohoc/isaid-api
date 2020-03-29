package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ProphetResourceStepDefs extends SpringAcceptanceTest {

    @Autowired
    private ProphetService prophetService;

    private Prophet actualProphet;

    private Exception throwedException;

    @Given("that exists a registered prophet with {string} as login and {string} as prophetCode")
    public void createProphet(String login, String prophetCode) throws IOException {
        prophetService.createProphet(login, prophetCode);
    }

    @When("clients makes a GET request to Prophet resource passing {string} as login")
    public void getProphet(String login) {
        try {
            actualProphet = prophetService.retrieveProphetBy(login);
        } catch (Exception e) {
            throwedException = e;
        }
    }

    @When("clients makes a POST request with {string} as login")
    public void createProphet(String login) {
        try {
            prophetService.createProphet(login);
        } catch (Exception e) {
            throwedException = e;
        }
    }

    @Then("the prophet {string} which code is {string} will be returned")
    public void assertProphet(String expectedLogin, String expectedProphetCode) {
        assertEquals(expectedLogin, actualProphet.getLogin());
        assertEquals(expectedProphetCode, actualProphet.getProphetCode());
    }

    @Then("no prophet should be returned")
    public void assertNullProphet() {
        assertNull(actualProphet);
    }

    @Then("a exception with the message {string} should be throwed")
    public void assertException(String expectedExceptionMessage) {
        assertEquals(expectedExceptionMessage, throwedException.getMessage());
    }

    @Then("a prophet with login equals to {string} should exist in the database")
    public void verifyProphetInDatabase(String expectedLogin) {
        Prophet expectedProphet = prophetService.retrieveProphetBy(expectedLogin);
        assertNotNull(expectedProphet);
        assertEquals(expectedLogin, expectedProphet.getLogin());
    }
}
