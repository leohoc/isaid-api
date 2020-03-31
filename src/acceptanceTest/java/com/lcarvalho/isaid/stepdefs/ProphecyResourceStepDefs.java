package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.application.resource.ProphecyResource;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.service.ProphecyService;
import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProphecyResourceStepDefs {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphecyResource prophecyResource;

    @Autowired
    private ProphecyService prophecyService;

    private ResponseEntity actualResponseEntity;

    @When("clients makes a POST request to {string} prophecies with {string} as summary and {string} as description")
    public void createProphecy(String prophetLogin, String summary, String description) throws ProphetNotFoundException {
        actualResponseEntity = prophecyResource.createProphecy(prophetLogin, summary, description);
    }

    @Then("a prophecy with {string} as prophetCode, {string} as summary and {string} as description should exist in the database")
    public void assertProphecy(String expectedProphetCode, String expectedSummary, String expectedDescription) {

        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(expectedProphetCode);

        if (actualProphecies != null && actualProphecies.size() > 0) {

            Prophecy actualProphecy = actualProphecies.get(0);
            Assertions.assertEquals(expectedProphetCode, actualProphecy.getProphetCode());
            Assertions.assertEquals(expectedSummary, actualProphecy.getSummary());
            Assertions.assertEquals(expectedDescription, actualProphecy.getDescription());
        }
    }

    @Then("a {int} http response will be returned by the Prophecy resource")
    public void assertHttpStatusResponse(Integer expectedHttpStatus) {
        assertEquals(HttpStatus.valueOf(expectedHttpStatus), actualResponseEntity.getStatusCode());
    }
}
