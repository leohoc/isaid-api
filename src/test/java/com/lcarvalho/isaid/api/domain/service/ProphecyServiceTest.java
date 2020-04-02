package com.lcarvalho.isaid.api.domain.service;

import com.lcarvalho.isaid.api.domain.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testCreateProphecy() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "Prophecy summary";
        String description = "Prophecy description";

        Prophet prophet = buildProphet(prophetLogin);
        Prophecy expectedProphecy = buildProphecy(prophet.getProphetCode(), summary, description);

        Mockito.when(prophetService.retrieveProphetBy(Mockito.eq(prophetLogin))).thenReturn(prophet);
        Mockito.when(prophecyRepository.save(Mockito.any())).thenReturn(expectedProphecy);

        // When
        Prophecy actualProphecy = prophecyService.createProphecy(prophetLogin, summary, description);
        Mockito.verify(prophecyRepository).save(prophecyCaptor.capture());

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
    public void testCreateProphecyWithEmptyProphetLogin() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "";
        String summary = "Prophecy summary";
        String description = "Prophecy description";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithNullProphetLogin() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = null;
        String summary = "Prophecy summary";
        String description = "Prophecy description";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithEmptySummary() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "";
        String description = "Prophecy description";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithNullSummary() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = null;
        String description = "Prophecy description";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithLenghtySummary() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tincidunt metus sit amet elit bibendum euismod. Curabitur rutrum lacinia amet.";
        String description = "Prophecy description";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithEmptyDescription() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "Prophecy summary";
        String description = "";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithNullDescription() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "Prophecy summary";
        String description = null;

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyWithLenghtyDescription() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "lorgana";
        String summary = "Prophecy summary";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan imperdiet neque, in pellentesque " +
                                "turpis suscipit id. Sed a pharetra nulla, vel porta ipsum. Integer nec nibh magna. Nunc interdum consectetur aliquet. " +
                                "Maecenas gravida commodo lectus non rutrum. Nam cras amet. ";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testCreateProphecyNonexistentProphetLogin() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        String prophetLogin = "as8das89da7sd9a";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        Mockito.when(prophetService.retrieveProphetBy(Mockito.eq(prophetLogin))).thenReturn(null);

        // When Then
        assertThrows(
                ProphetNotFoundException.class,
                () -> prophecyService.createProphecy(prophetLogin, summary, description));
    }

    @Test
    public void testRetrieveProphecies() {

        // Given
        List<Prophecy> expectedProphecies = buildProphecies(PROPHET_CODE);
        Mockito.when(prophecyRepository.findByProphetCode(Mockito.eq(PROPHET_CODE))).thenReturn(expectedProphecies);

        // When
        List<Prophecy> actualProphecies = prophecyService.retrievePropheciesBy(PROPHET_CODE);

        // Then
        assertEquals(expectedProphecies.size(), actualProphecies.size());
        for (Prophecy expectedProphecy : expectedProphecies) {
            assertTrue(actualProphecies.contains(expectedProphecy));
        }
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
        return new Prophecy(prophetCode, summary, description);
    }
}