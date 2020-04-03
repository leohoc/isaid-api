package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphecyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    public void testCreateProphecy() throws ProphetNotFoundException, InvalidParameterException {

        // Given
        String prophetLogin = "hsolo";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        Prophecy expectedProphecy = buildProphecy(summary, description);

        when(prophecyService.createProphecy(eq(prophetLogin), eq(summary), eq(description))).thenReturn(expectedProphecy);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecy.getProphetCode(), ((Prophecy)actualResponseEntity.getBody()).getProphetCode());
        assertEquals(expectedProphecy.getSummary(), ((Prophecy)actualResponseEntity.getBody()).getSummary());
        assertEquals(expectedProphecy.getDescription(), ((Prophecy)actualResponseEntity.getBody()).getDescription());
    }

    @Test
    public void testCreateProphecyWithInvalidParameter() throws ProphetNotFoundException, InvalidParameterException {

        // Given
        String prophetLogin = "hsolo";
        String summary = null;
        String description = null;

        when(prophecyService.createProphecy(eq(prophetLogin), eq(summary), eq(description))).thenThrow(new InvalidParameterException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    @Test
    public void testCreateProphecyWithNonexistentProphetLogin() throws ProphetNotFoundException, InvalidParameterException {

        // Given
        String prophetLogin = "kj12i31i2u3h98y";
        String summary = "Prophecy summary";
        String description = "Prophecy description";

        when(prophecyService.createProphecy(eq(prophetLogin), eq(summary), eq(description))).thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    @Test
    public void testGetAllPropheciesOfAProphet() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet();
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        List<Prophecy> expectedProphecies = buildProphecies(prophet, 1);

        when(prophecyService
                .retrievePropheciesBy(eq(prophet.getLogin()), eq(startDateTime), eq(endDateTime)))
                .thenReturn(expectedProphecies);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophet.getLogin(), startDateTime, endDateTime);
        List<Prophecy> actualProphecies = (List<Prophecy>)actualResponseEntity.getBody();

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testGetAllPropheciesOfAProphetWithinATimeRange() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet();
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        List<Prophecy> expectedProphecies = buildProphecies(prophet, 3);

        when(prophecyService
                .retrievePropheciesBy(eq(prophet.getLogin()), eq(startDateTime), eq(endDateTime)))
                .thenReturn(expectedProphecies);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophet.getLogin(), startDateTime, endDateTime);
        List<Prophecy> actualProphecies = (List<Prophecy>)actualResponseEntity.getBody();

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testGetAllPropheciesOfAProphetWithInvalidLogin() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "";
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        when(prophecyService
                .retrievePropheciesBy(eq(prophetLogin), eq(startDateTime), eq(endDateTime)))
                .thenThrow(new InvalidParameterException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophetLogin, startDateTime, endDateTime);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseEntity.getStatusCode());
    }

    @Test
    public void testGetAllPropheciesOfANonexistentProphet() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "as0d0ad8n0as8da";
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        when(prophecyService
                .retrievePropheciesBy(eq(prophetLogin), eq(startDateTime), eq(endDateTime)))
                .thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.getPropheciesBy(prophetLogin, startDateTime, endDateTime);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
    }

    private Prophecy buildProphecy(String summary, String description) {
        return new Prophecy(PROPHET_CODE, summary, description);
    }

    private Prophet buildProphet() {
        String login = "jhutt";
        String prophetCode = "a4f3edc8-e024-4c07-994c-fe6c6e36ee9b";
        return new Prophet(login, prophetCode);
    }

    private List<Prophecy> buildProphecies(Prophet prophet, int size) {

        List<Prophecy> prophecies = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            String summary = String.format("Prophecy %s summary", i);
            String description = String.format("Prophecy %s description", i);
            prophecies.add(new Prophecy(prophet.getProphetCode(), summary, description));
        }
        return prophecies;
    }
}