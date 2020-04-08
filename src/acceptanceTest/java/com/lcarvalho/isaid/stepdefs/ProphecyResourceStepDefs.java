package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.application.resource.ProphecyResource;
import com.lcarvalho.isaid.api.domain.dto.ProphecyDTO;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.service.ProphecyService;
import com.lcarvalho.isaid.commons.HttpClient;
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
import java.util.Arrays;
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

    @Autowired
    private HttpClient httpClient;

    private ResponseEntity actualResponseEntity;

    @Given("the following prophecies exists:")
    public void createProphecies(List<Prophecy> prophecies) {
        prophecyRepository.saveAll(prophecies);
    }

    @When("clients makes a POST request to the Prophecy resource with {string} uri and {string} in the body")
    public void postRequestToProphecy(String uri, String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, ProphecyDTO.class);
    }

    @When("clients makes a GET request to the Prophecy resource with {string} uri")
    public void getProphecies(String uri) {
        actualResponseEntity = httpClient.get(uri, ProphecyDTO[].class);
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
            List<ProphecyDTO> actualProphecies = Arrays.asList((ProphecyDTO[])actualResponseEntity.getBody());
            assertEquals(expectedSize.intValue(), actualProphecies.size());
        }
    }

    @Then("the following prophecies will be returned in the response body")
    public void assertResponseBodyProphecies(List<ProphecyDTO> expectedProphecies) {
        List<ProphecyDTO> actualProphecies = Arrays.asList((ProphecyDTO[])actualResponseEntity.getBody());
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
