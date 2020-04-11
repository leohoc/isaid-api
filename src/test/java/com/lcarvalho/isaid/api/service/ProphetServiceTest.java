package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.ProphetRequest;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(prophetRepository.findByLogin(eq(LOGIN))).thenReturn(null);
        when(prophetRepository.save(any())).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetService.createProphet(buildProphetRequest(LOGIN));

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testCreateAlreadyExistentProphet() {

        // Given
        when(prophetRepository.findByLogin(eq(LOGIN))).thenReturn(buildProphet());

        // When Then
        assertThrows(
                ProphetAlreadyExistsException.class,
                () -> prophetService.createProphet(buildProphetRequest(LOGIN)));
    }

    @Test
    public void testRetrieveProphet() throws ProphetNotFoundException {

        // Given
        Prophet expectedProphet = buildProphet();
        when(prophetRepository.findByLogin(eq(LOGIN))).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetService.retrieveProphetBy(LOGIN);

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testRetrieveNonexistentProphet() {

        // Given
        when(prophetRepository.findByLogin(eq(LOGIN))).thenReturn(null);

        // When Then
        assertThrows(
                ProphetNotFoundException.class,
                () -> prophetService.retrieveProphetBy(LOGIN));
    }

    private Prophet buildProphet() {
        return new Prophet(LOGIN, PROPHET_CODE);
    }

    private ProphetRequest buildProphetRequest(final String login) {
        ProphetRequest prophetRequest = new ProphetRequest();
        prophetRequest.setLogin(login);
        return prophetRequest;
    }
}