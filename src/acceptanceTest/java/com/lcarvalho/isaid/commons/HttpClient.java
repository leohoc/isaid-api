package com.lcarvalho.isaid.commons;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {

    private static final String SERVER_URL = "http://localhost";

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getHostEndpoint() {
        return SERVER_URL + ":" + port;
    }

    public ResponseEntity get(final String uri, final Class clazz) {
        try {
            return restTemplate.getForEntity(getHostEndpoint() + uri, clazz);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getStatusCode());
        }
    }

    public ResponseEntity post(final String uri, final String jsonBodyRequest, final Class clazz) {
        try {
            return restTemplate.postForEntity(getHostEndpoint() + uri, buildRequest(jsonBodyRequest), clazz);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getStatusCode());
        }
    }

    private HttpEntity<String> buildRequest(final String jsonBodyRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<String>(jsonBodyRequest, headers);
    }
}
