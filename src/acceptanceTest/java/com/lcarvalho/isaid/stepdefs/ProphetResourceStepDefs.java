package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ProphetResourceStepDefs extends SpringAcceptanceTest {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphetService prophetService;

    private Prophet prophet;

    @Given("that exists a registered prophet with {string} as login and {string} as prophetCode")
    public void createProphet(String login, String prophetCode) throws IOException {
        prophetService.createProphet(login, prophetCode);
    }

    @When("clients makes a GET request to Prophet resource passing {string} as login")
    public void getProphet(String login) {
        prophet = prophetService.retrieveProphetBy(login);
    }

    @Then("the prophet {string} which code is {string} will be returned")
    public void assertProphet(String login, String prophetCode) {
        Assertions.assertEquals(login, prophet.getLogin());
        Assertions.assertEquals(prophetCode, prophet.getProphetCode());
    }
}
