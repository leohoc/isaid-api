package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.application.resource.ProphecyResource;
import com.lcarvalho.isaid.api.domain.dto.ProphecyDTO;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.service.ProphecyService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProphecyResourceStepDefs {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphecyResource prophecyResource;

    @Autowired
    private ProphecyService prophecyService;

    @Autowired
    private ProphecyRepository prophecyRepository;

    private ResponseEntity actualResponseEntity;

    @Given("the following prophecies exists:")
    public void createProphecies(List<Prophecy> prophecies) {
        prophecyRepository.saveAll(prophecies);
    }

    @When("clients makes a POST request to {string} prophecies with {string} as summary and {string} as description")
    public void postRequestToProphecy(final String prophetLogin, final String summary, final String description) throws ProphetNotFoundException {
        actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new ProphecyDTO(summary, description));
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

    @Then("a {int} http response will be returned by the Prophecy resource")
    public void assertHttpStatusResponse(final Integer expectedHttpStatus) {
        assertEquals(HttpStatus.valueOf(expectedHttpStatus), actualResponseEntity.getStatusCode());
    }

    @Then("{word} the database for a prophecy with {string}, {string} and {string}")
    public void assertProphecyInDatabase(final String searchDatabase, final String expectedProphetCode, final String expectedSummary, final String expectedDescription) {
        if (Boolean.valueOf(searchDatabase)) {
            List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(expectedProphetCode);

            if (actualProphecies != null && actualProphecies.size() > 0) {

                Prophecy actualProphecy = actualProphecies.get(0);
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
            List<ProphecyDTO> actualProphecies = (List<ProphecyDTO>) actualResponseEntity.getBody();
            assertEquals(expectedSize.intValue(), actualProphecies.size());
        }
    }

    @Then("the following prophecies will be returned in the response body")
    public void assertResponseBodyProphecies(List<ProphecyDTO> expectedProphecies) {
        List<ProphecyDTO> actualProphecies = (List<ProphecyDTO>) actualResponseEntity.getBody();
        assertEquals(expectedProphecies, actualProphecies);
    }

    @DataTableType
    public List<Prophecy> getProphecies(DataTable table) {
        return table.asMaps().stream()
                .map(m -> new Prophecy(m.get("prophetCode"), LocalDateTime.parse(m.get("prophecyTimestamp")), m.get("summary"), m.get("description")))
                .collect(Collectors.toList());
    }

    @DataTableType
    public List<ProphecyDTO> getProphecyDTOList(DataTable table) {
        return table.asMaps().stream()
                .map(m -> new ProphecyDTO(m.get("prophetCode"), LocalDateTime.parse(m.get("prophecyTimestamp")), m.get("summary"), m.get("description")))
                .collect(Collectors.toList());
    }
}
