package com.lcarvalho.isaid.stepdefs;

import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import com.lcarvalho.isaid.api.service.ProphetService;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.commons.HttpClient;
import com.lcarvalho.isaid.config.SpringAcceptanceTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ProphetResourceStepDefs extends SpringAcceptanceTest {

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private ProphetRepository prophetRepository;

    @Autowired
    private HttpClient httpClient;

    private  ResponseEntity actualResponseEntity;

    @Given("the following prophets exists:")
    public void createProphets(final List<Prophet> prophetList) {
        prophetRepository.saveAll(prophetList);
    }

    @When("clients makes a GET request to the Prophet resource with {string} uri")
    public void getProphet(final String uri) {
        actualResponseEntity = httpClient.get(uri, Prophet.class);
    }

    @When("clients makes a POST request to the Prophet resource with {string} uri and {string} in the body")
    public void createProphet(final String uri, final String jsonBodyRequest) {
        actualResponseEntity = httpClient.post(uri, jsonBodyRequest, Prophet.class);
    }

    @Then("{word} in the body a prophet with {string} as login and {string} as code")
    public void assertGetProphetResponse(final String verifyResponseBody, final String expectedLogin, final String expectedProphetCode) {

        if (Boolean.valueOf(verifyResponseBody)) {
            assertEquals(expectedLogin, ((Prophet)actualResponseEntity.getBody()).getLogin());
            assertEquals(expectedProphetCode, ((Prophet)actualResponseEntity.getBody()).getProphetCode());
        }
    }

    @Then("{word} a prophet with login equals to {string} in the database")
    public void verifyProphetInDatabase(final String verifyDatabase, final String expectedLogin) throws ProphetNotFoundException {
        if (Boolean.valueOf(verifyDatabase)) {
            Prophet expectedProphet = prophetService.retrieveProphetBy(expectedLogin);
            assertNotNull(expectedProphet);
            assertEquals(expectedLogin, expectedProphet.getLogin());
        }
    }

    @Then("a {int} http response will be returned by the Prophet resource")
    public void assertHttpStatusResponse(final Integer expectedHttpStatus) {
        assertEquals(HttpStatus.valueOf(expectedHttpStatus), actualResponseEntity.getStatusCode());
    }

    @DataTableType
    public List<Prophet> getProphets(final DataTable table) {
        return table.asMaps().stream().map(m -> new Prophet(m.get("login"), m.get("prophetCode"))).collect(Collectors.toList());
    }
}
