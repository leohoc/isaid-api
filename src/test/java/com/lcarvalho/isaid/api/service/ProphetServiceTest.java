package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.ProphetDTO;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void testCreateProphet() throws ProphetAlreadyExistsException, InvalidParameterException {

        // Given
        ProphetDTO expectedProphet = buildProphet();
        Mockito.when(prophetRepository.findByLogin(Mockito.any())).thenReturn(null);
        Mockito.when(prophetRepository.save(Mockito.any())).thenReturn(convertToProphet(expectedProphet));

        // When
        ProphetDTO actualProphet = prophetService.createProphet(LOGIN);

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
        Mockito.when(prophetRepository.findByLogin(Mockito.eq(LOGIN))).thenReturn(convertToProphet(buildProphet()));

        // When Then
        assertThrows(
                ProphetAlreadyExistsException.class,
                () -> prophetService.createProphet(LOGIN));
    }

    @Test
    public void testRetrieveProphet() throws InvalidParameterException {

        // Given
        ProphetDTO expectedProphet = buildProphet();
        Mockito.when(prophetRepository.findByLogin(Mockito.anyString())).thenReturn(convertToProphet(expectedProphet));

        // When
        ProphetDTO actualProphet = prophetService.retrieveProphetBy(LOGIN);

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

    private ProphetDTO buildProphet() {
        return new ProphetDTO(LOGIN, PROPHET_CODE);
    }

    private Prophet convertToProphet(ProphetDTO prophetDTO) {
        return new Prophet(prophetDTO.getLogin(), prophetDTO.getProphetCode());
    }

}