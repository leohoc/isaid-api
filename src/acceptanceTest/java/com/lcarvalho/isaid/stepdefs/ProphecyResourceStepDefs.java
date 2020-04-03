package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.application.resource.ProphecyResource;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.service.ProphecyService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProphecyResourceStepDefs {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphecyResource prophecyResource;

    @Autowired
    private ProphecyService prophecyService;

    private ResponseEntity actualResponseEntity;

    @Given("that exists a stored prophecy with {string} as prophetCode, {string} as prophecyTimestamp, {string} as summary and {string} as description")
    public void createProphecy(final String prophetCode, final String prophecyTimestamp, final String summary, final String description) {
        prophecyService.createProphecy(new Prophecy(prophetCode, LocalDateTime.parse(prophecyTimestamp), summary, description));
    }

    @When("clients makes a POST request to {string} prophecies with {string} as summary and {string} as description")
    public void postRequestToProphecy(final String prophetLogin, final String summary, final String description) throws ProphetNotFoundException {
        actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));
    }

    @When("clients makes a GET request to {string} prophecies")
    public void clientsMakesAGETRequestToProphecies(String prophetLogin) {
        clientsMakesAGETRequestToProphecies(prophetLogin, null, null);
    }

    @When("clients makes a GET request to {string} prophecies filtering by prophecies latest than {string}")
    public void clientsMakesAGETRequestToPropheciesLatestThanStartDateTime(String prophetLogin, String startDateTimeString) {
        clientsMakesAGETRequestToProphecies(prophetLogin, startDateTimeString, null);
    }

    @When("clients makes a GET request to {string} prophecies filtering by prophecies older than {string}")
    public void clientsMakesAGETRequestToPropheciesOldestThanEndDateTime(String prophetLogin, String endDateTimeString) {
        clientsMakesAGETRequestToProphecies(prophetLogin, null, endDateTimeString);
    }

    @When("clients makes a GET request to {string} prophecies filtering by prophecies between {string} and {string}")
    public void clientsMakesAGETRequestToProphecies(final String prophetLogin, final String startDateTimeString, final String endDateTimeString) {

        LocalDateTime startDateTime = startDateTimeString == null ? null : LocalDateTime.parse(startDateTimeString);
        LocalDateTime endDateTime = endDateTimeString == null ? null : LocalDateTime.parse(endDateTimeString);

        actualResponseEntity = prophecyResource.getPropheciesBy(prophetLogin, startDateTime, endDateTime);
    }

    @Then("a prophecy with {string} as prophetCode, {string} as summary and {string} as description should exist in the database")
    public void assertProphecy(final String expectedProphetCode, final String expectedSummary, final String expectedDescription) {

        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(expectedProphetCode);

        if (actualProphecies != null && actualProphecies.size() > 0) {

            Prophecy actualProphecy = actualProphecies.get(0);
            assertEquals(expectedProphetCode, actualProphecy.getProphetCode());
            assertEquals(expectedSummary, actualProphecy.getSummary());
            assertEquals(expectedDescription, actualProphecy.getDescription());
        }
    }

    @Then("a {int} http response will be returned by the Prophecy resource")
    public void assertHttpStatusResponse(final Integer expectedHttpStatus) {
        assertEquals(HttpStatus.valueOf(expectedHttpStatus), actualResponseEntity.getStatusCode());
    }

    @Then("a prophecy with {string} as prophetCode, {string} as prophecyTimestamp, {string} as summary and {string} as description should be returned in the response body")
    public void assertResponseBodyProphecy(String prophetCode, String prophecyTimestamp, String summary, String description) {

        Prophecy expectedProphecy = new Prophecy(prophetCode, LocalDateTime.parse(prophecyTimestamp), summary, description);
        List<Prophecy> actualProphecies = (List<Prophecy>) actualResponseEntity.getBody();
        assertTrue(actualProphecies.contains(expectedProphecy));
    }

    @Then("a prophecy list with {int} elements should be returned in the response body")
    public void assertResponseBodyEmptyProphecies(final Integer expectedSize) {
        List<Prophecy> actualProphecies = (List<Prophecy>) actualResponseEntity.getBody();
        assertEquals(expectedSize.intValue(), actualProphecies.size());
    }
}
