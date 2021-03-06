package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.ProphecyRequest;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.service.ProphecyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProphecyResourceTest {

    private static final String PROPHET_CODE = "8104fb98-d168-42ba-8244-fed780e68279";

    @Mock
    private ProphecyService prophecyService;

    @InjectMocks
    private ProphecyResource prophecyResource;

    @Test
    public void testCreateProphecy() throws ProphetNotFoundException {

        // Given
        String prophetLogin = "hsolo";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);
        Prophecy expectedProphecy = buildProphecy(summary, description);

        when(prophecyService.createProphecy(eq(prophetLogin), eq(prophecyRequest))).thenReturn(expectedProphecy);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, prophecyRequest);

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecy.getProphetCode(), ((Prophecy)actualResponseEntity.getBody()).getProphetCode());
        assertEquals(expectedProphecy.getSummary(), ((Prophecy)actualResponseEntity.getBody()).getSummary());
        assertEquals(expectedProphecy.getDescription(), ((Prophecy)actualResponseEntity.getBody()).getDescription());
    }

    @Test
    public void testCreateProphecyWithInvalidLogin() {

        // Given
        String prophetLogin = "";
        String summary = "Prophecy summary";
        String description = "Prophecy description";

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, buildProphecyRequest(summary, description));

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    @Test
    public void testCreateProphecyWithInvalidProphecyRequest() {

        // Given
        String prophetLogin = "hsolo";
        String summary = null;
        String description = null;

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, buildProphecyRequest(summary, description));

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    @Test
    public void testCreateProphecyWithNonexistentProphetLogin() throws ProphetNotFoundException {

        // Given
        String prophetLogin = "kj12i31i2u3h98y";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        when(prophecyService.createProphecy(eq(prophetLogin), eq(prophecyRequest))).thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, prophecyRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    @Test
    public void testGetAllPropheciesOfAProphet() throws ProphetNotFoundException {

        // Given
        Integer page = 0;
        Prophet prophet = buildProphet();
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        Page<Prophecy> expectedProphecies = buildPropheciesPage(prophet, 1);

        when(prophecyService
                .retrievePropheciesBy(eq(prophet.getLogin()), eq(startDateTime), eq(endDateTime), eq(page)))
                .thenReturn(expectedProphecies);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophet.getLogin(), startDateTime, endDateTime, page);
        List<Prophecy> actualProphecies = (List<Prophecy>)actualResponseEntity.getBody();

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecies.getContent().size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies.getContent()) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testGetAllPropheciesOfAProphetWithinATimeRange() throws ProphetNotFoundException {

        // Given
        Integer page = 0;
        Prophet prophet = buildProphet();
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        Page<Prophecy> expectedProphecies = buildPropheciesPage(prophet, 3);

        when(prophecyService
                .retrievePropheciesBy(eq(prophet.getLogin()), eq(startDateTime), eq(endDateTime), eq(page)))
                .thenReturn(expectedProphecies);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophet.getLogin(), startDateTime, endDateTime, page);
        List<Prophecy> actualProphecies = (List<Prophecy>)actualResponseEntity.getBody();

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecies.getContent().size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies.getContent()) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testGetAllPropheciesOfAProphetWithInvalidLogin() {

        // Given
        Integer page = 0;
        String prophetLogin = "";
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophetLogin, startDateTime, endDateTime, page);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseEntity.getStatusCode());
    }

    @Test
    public void testGetAllPropheciesOfANonexistentProphet() throws ProphetNotFoundException {

        // Given
        Integer page = 0;
        String prophetLogin = "as0d0ad8n0as8da";
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        when(prophecyService
                .retrievePropheciesBy(eq(prophetLogin), eq(startDateTime), eq(endDateTime), eq(page)))
                .thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophetLogin, startDateTime, endDateTime, page);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
    }

    private ProphecyRequest buildProphecyRequest(final String summary, final String description) {
        ProphecyRequest prophecyRequest = new ProphecyRequest();
        prophecyRequest.setSummary(summary);
        prophecyRequest.setDescription(description);
        return prophecyRequest;
    }

    private Prophecy buildProphecy(final String summary, final String description) {
        return new Prophecy(PROPHET_CODE, LocalDateTime.now(), summary, description);
    }

    private Prophet buildProphet() {
        String login = "jhutt";
        String prophetCode = "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b";
        return new Prophet(login, prophetCode);
    }

    private List<Prophecy> buildProphecies(final Prophet prophet, final int size) {

        List<Prophecy> prophecies = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            String summary = String.format("Prophecy %s summary", i);
            String description = String.format("Prophecy %s description", i);
            prophecies.add(new Prophecy(prophet.getProphetCode(), LocalDateTime.now(), summary, description));
        }
        return prophecies;
    }

    private Page<Prophecy> buildPropheciesPage(final Prophet prophet, final int size) {
        return new PageImpl<>(buildProphecies(prophet, size));
    }
}