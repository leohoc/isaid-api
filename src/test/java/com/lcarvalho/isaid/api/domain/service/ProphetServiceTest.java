package com.lcarvalho.isaid.api.domain.service;

import com.lcarvalho.isaid.api.domain.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProphetServiceTest {

    private static final String LOGIN = "lcarvalho";
    private static final String PROPHET_CODE = "2fe2c61b-aaef-4633-977d-2273ee993d90";

    @Mock
    private ProphetRepository prophetRepository;

    @InjectMocks
    private ProphetService prophetService;

    @Test
    public void testCreateProphet() throws ProphetAlreadyExistsException {

        // Given
        Prophet expectedProphet = buildProphet();
        Mockito.when(prophetRepository.findByLogin(Mockito.any())).thenReturn(null);
        Mockito.when(prophetRepository.save(Mockito.any())).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetService.createProphet(LOGIN);

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testCreateProphetWithNullLogin() {

        // Given
        String login = null;

        // When Then
        assertThrows(
                    InvalidParameterException.class,
                    () -> prophetService.createProphet(login));
    }

    @Test
    public void testCreateProphetWithEmptyLogin() {

        // Given
        String login = "";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophetService.createProphet(login));
    }

    @Test
    public void testCreateAlreadyExistentProphet() {

        // Given
        Mockito.when(prophetRepository.findByLogin(Mockito.eq(LOGIN))).thenReturn(buildProphet());

        // When Then
        assertThrows(
                ProphetAlreadyExistsException.class,
                () -> prophetService.createProphet(LOGIN));
    }

    @Test
    public void testRetrieveProphet() {

        // Given
        Prophet expectedProphet = buildProphet();
        Mockito.when(prophetRepository.findByLogin(Mockito.anyString())).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetService.retrieveProphetBy(LOGIN);

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testRetrieveProphetByNullLogin() {

        // Given
        String login = null;

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophetService.retrieveProphetBy(login));
    }

    @Test
    public void testRetrieveProphetByEmptyLogin() {

        // Given
        String login = "";

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophetService.retrieveProphetBy(login));
    }

    public Prophet buildProphet() {
        return new Prophet(LOGIN, PROPHET_CODE);
    }

}