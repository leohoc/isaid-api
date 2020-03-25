package com.lcarvalho.isaid.api.domain.service;

import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;

@ExtendWith(MockitoExtension.class)
class ProphetServiceTest {

    private static final String LOGIN = "lcarvalho";
    private static final String PROPHET_CODE = "2fe2c61b-aaef-4633-977d-2273ee993d90";

    @Mock
    private ProphetRepository prophetRepository;

    @InjectMocks
    private ProphetService prophetService;

    @Test
    public void testCreateProphet() {

        // Given
        Prophet expectedProphet = buildProphet();
        Mockito.when(prophetRepository.save(Mockito.any())).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetService.createProphet(LOGIN);

        // Then
        Assertions.assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testCreateProphetWithNullLogin() {

        // Given
        String login = null;

        // When Then
        Assertions.assertThrows(
                    InvalidParameterException.class,
                    () -> prophetService.createProphet(login));
    }

    @Test
    public void testCreateProphetWithEmptyLogin() {

        // Given
        String login = "";

        // When Then
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> prophetService.createProphet(login));
    }

    public void testRetrieveProphet() {

        // Given
        Prophet expectedProphet = buildProphet();
        Mockito.when(prophetRepository.findByLogin(Mockito.anyString())).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetService.retrieveProphetBy(LOGIN);

        // Then
        Assertions.assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testRetrieveProphetByNullLogin() {

        // Given
        String login = null;

        // When Then
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> prophetService.retrieveProphetBy(login));
    }

    @Test
    public void testRetrieveProphetByEmptyLogin() {

        // Given
        String login = "";

        // When Then
        Assertions.assertThrows(
                InvalidParameterException.class,
                () -> prophetService.retrieveProphetBy(login));
    }

    public Prophet buildProphet() {
        return new Prophet(LOGIN, PROPHET_CODE);
    }

}