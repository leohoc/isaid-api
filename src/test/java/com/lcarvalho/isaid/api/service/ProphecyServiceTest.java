package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.ProphecyRequest;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProphecyServiceTest {

    private static final String PROPHET_CODE = "8104fb98-d168-42ba-8244-fed780e68279";

    @Captor
    private ArgumentCaptor<Prophecy> prophecyCaptor;

    @Mock
    private ProphecyRepository prophecyRepository;

    @Mock
    private ProphetService prophetService;

    @InjectMocks
    private ProphecyService prophecyService;

    @Test
    public void testCreateProphecy() throws ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        Prophet prophet = buildProphet(prophetLogin);
        Prophecy expectedProphecy = buildProphecy(prophet.getProphetCode(), summary, description);

        when(prophetService.retrieveProphetBy(eq(prophetLogin))).thenReturn(prophet);
        when(prophecyRepository.save(any())).thenReturn(expectedProphecy);

        // When
        Prophecy actualProphecy = prophecyService.createProphecy(prophetLogin, prophecyRequest);
        verify(prophecyRepository).save(prophecyCaptor.capture());

        // Then

        // Asserting captured prophecy passed to the repository save method
        assertEquals(expectedProphecy.getProphetCode(), prophecyCaptor.getValue().getProphetCode());
        assertEquals(expectedProphecy.getSummary(), prophecyCaptor.getValue().getSummary());
        assertEquals(expectedProphecy.getDescription(), prophecyCaptor.getValue().getDescription());
        assertEquals(expectedProphecy.getProphecyTimestamp().toLocalDate(), prophecyCaptor.getValue().getProphecyTimestamp().toLocalDate());

        // Asserting the actual returned prophecy
        assertEquals(expectedProphecy.getProphetCode(), actualProphecy.getProphetCode());
        assertEquals(expectedProphecy.getSummary(), actualProphecy.getSummary());
        assertEquals(expectedProphecy.getDescription(), actualProphecy.getDescription());
        assertEquals(expectedProphecy.getProphecyTimestamp().toLocalDate(), actualProphecy.getProphecyTimestamp().toLocalDate());
    }

    @Test
    public void testCreateProphecyNonexistentProphetLogin() throws ProphetNotFoundException {

        // Given
        String prophetLogin = "as8das89da7sd9a";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);
        when(prophetService.retrieveProphetBy(eq(prophetLogin))).thenThrow(new ProphetNotFoundException());

        // When Then
        assertThrows(
                ProphetNotFoundException.class,
                () -> prophecyService.createProphecy(prophetLogin, prophecyRequest));
    }

    @Test
    public void testRetrieveAllPropheciesOfAProphet() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet("wantilles");
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        List<Prophecy> expectedProphecies = buildProphecies(PROPHET_CODE);
        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenReturn(prophet);
        when(prophecyRepository.findByProphetCode(eq(prophet.getProphetCode()))).thenReturn(expectedProphecies);

        // When
        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(prophet.getLogin(), startDateTime, endDateTime);

        // Then
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testRetrieveAllPropheciesOfAProphetWithinATimeRange() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet("wantilles");
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);

        List<Prophecy> expectedProphecies = buildProphecies(PROPHET_CODE);
        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenReturn(prophet);
        when(prophecyRepository
                .findByProphetCodeAndProphecyTimestampBetween(eq(prophet.getProphetCode()), eq(startDateTime), eq(endDateTime)))
                .thenReturn(expectedProphecies);

        // When
        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(prophet.getLogin(), startDateTime, endDateTime);

        // Then
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testRetrieveAllPropheciesOfAProphetAfterASpecificTime() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet("wantilles");
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = null;

        List<Prophecy> expectedProphecies = buildProphecies(PROPHET_CODE);
        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenReturn(prophet);
        when(prophecyRepository
                .findByProphetCodeAndProphecyTimestampGreaterThan(eq(prophet.getProphetCode()), eq(startDateTime)))
                .thenReturn(expectedProphecies);

        // When
        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(prophet.getLogin(), startDateTime, endDateTime);

        // Then
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testRetrieveAllPropheciesOfAProphetBeforeASpecificTime() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet("wantilles");
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay().plusDays(1);

        List<Prophecy> expectedProphecies = buildProphecies(PROPHET_CODE);
        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenReturn(prophet);
        when(prophecyRepository
                .findByProphetCodeAndProphecyTimestampLessThan(eq(prophet.getProphetCode()), eq(endDateTime)))
                .thenReturn(expectedProphecies);

        // When
        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(prophet.getLogin(), startDateTime, endDateTime);

        // Then
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
    }

    @Test
    public void testRetrieveAllPropheciesOfANonexistentProphet() throws ProphetNotFoundException {

        // Given
        Prophet prophet = buildProphet("sdkfsdf07sd89f7s");
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        when(prophetService.retrieveProphetBy(eq(prophet.getLogin()))).thenThrow(new ProphetNotFoundException());

        // When Then
        assertThrows(
                ProphetNotFoundException.class,
                () -> prophecyService.retrievePropheciesBy(prophet.getLogin(), startDateTime, endDateTime));
    }

    private List<Prophecy> buildProphecies(String prophetCode) {
        List<Prophecy> prophecies = new ArrayList<>();
        prophecies.add(buildProphecy(prophetCode, "Prophecy summary 1", "Prophecy summary 1"));
        prophecies.add(buildProphecy(prophetCode, "Prophecy summary 2", "Prophecy summary 2"));
        prophecies.add(buildProphecy(prophetCode, "Prophecy summary 3", "Prophecy summary 3"));
        return prophecies;
    }

    private Prophet buildProphet(String login) {
        return new Prophet(login, PROPHET_CODE);
    }

    private Prophecy buildProphecy(String prophetCode, String summary, String description) {
        return new Prophecy(prophetCode, LocalDateTime.now(), summary, description);
    }

    private ProphecyRequest buildProphecyRequest(final String summary, final String description) {
        ProphecyRequest prophecyRequest = new ProphecyRequest();
        prophecyRequest.setSummary(summary);
        prophecyRequest.setDescription(description);
        return prophecyRequest;
    }
}