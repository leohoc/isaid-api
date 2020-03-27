package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ProphetResourceStepDefs extends SpringAcceptanceTest {

    private static Logger LOGGER = LogManager.getLogger();

    @Given("^that exists a registered prophet with lskywalker as login and c25b1d8b-4246-408c-8521-937cf13a38be as prophetCode$")
    public void createProphet() throws IOException {
        LOGGER.info("createProhet");
    }

    @When("^clients makes a GET request to /prophet/lskywalker$")
    public void getProphet() {
        LOGGER.info("getProhet");
    }

    @When("^the prophet lskywalker which code is c25b1d8b-4246-408c-8521-937cf13a38be will be returned$")
    public void assertProphet() {
        LOGGER.info("assertProhet");
    }
}
