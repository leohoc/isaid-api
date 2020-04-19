package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.application.resource.ProphecyResource;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.service.ProphecyService;
import com.lcarvalho.isaid.commons.HttpClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProphecyResourceStepDefs {

    @Autowired
    private ProphecyResource prophecyResource;

    @Autowired
    private ProphecyService prophecyService;

    @Autowired
    private ProphecyRepository prophecyRepository;

    @Autowired
    private HttpClient httpClient;

    private ResponseEntity actualResponseEntity;

    @Given("the following prophecies exists:")
    public void createProphecies(final List<Prophecy> prophecies) {
        prophecyRepository.saveAll(prophecies);
    }

    @When("clients makes a POST request to the Prophecy resource with {string} uri and {string} in the body")
    public void postRequestToProphecy(final String uri, final String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, Prophecy.class);
    }

    @When("clients makes a GET request to the Prophecy resource with {string} uri")
    public void getProphecies(final String uri) {
        actualResponseEntity = httpClient.get(uri, Prophecy[].class);
    }

    @Then("a {int} http response will be returned by the Prophecy resource")
    public void assertHttpStatusResponse(final Integer expectedHttpStatus) {
        assertEquals(HttpStatus.valueOf(expectedHttpStatus), actualResponseEntity.getStatusCode());
    }

    @Then("{word} the database for a prophecy with {string}, {string} and {string}")
    public void assertProphecyInDatabase(final String searchDatabase, final String expectedProphetCode, final String expectedSummary, final String expectedDescription) {
        if (Boolean.valueOf(searchDatabase)) {
            Page<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(expectedProphetCode);

            if (actualProphecies != null && actualProphecies.getContent().size() > 0) {

                Prophecy actualProphecy = actualProphecies.getContent().get(0);
                assertEquals(expectedProphetCode, actualProphecy.getProphetCode());
                assertEquals(expectedSummary, actualProphecy.getSummary());
                assertEquals(expectedDescription, actualProphecy.getDescription());
            }
        }
    }

    @Then("a prophecy list with {int} elements should be returned in the response body")
    public void assertResponseBodyProphecyListSize(final Integer expectedSize) {
        this.assertResponseBodyProphecyListSize(Boolean.TRUE.toString(), expectedSize);
    }

    @Then("{word} a prophecy list with {int} elements should be returned in the response body")
    public void assertResponseBodyProphecyListSize(final String verifyResponseBody, final Integer expectedSize) {
        if (Boolean.valueOf(verifyResponseBody)) {
            List<Prophecy> actualProphecies = Arrays.asList((Prophecy[])actualResponseEntity.getBody());
            assertEquals(expectedSize.intValue(), actualProphecies.size());
        }
    }

    @Then("the following prophecies will be returned in the response body")
    public void assertResponseBodyProphecies(final List<Prophecy> expectedProphecies) {
        List<Prophecy> actualProphecies = Arrays.asList((Prophecy[])actualResponseEntity.getBody());
        assertEquals(expectedProphecies, actualProphecies);
    }

    @DataTableType
    public List<Prophecy> convertToProphecyList(final DataTable table) {
        return table.asMaps().stream()
                .map(m -> new Prophecy(m.get("prophetCode"), LocalDateTime.parse(m.get("prophecyTimestamp")), m.get("summary"), m.get("description")))
                .collect(Collectors.toList());
    }
}
