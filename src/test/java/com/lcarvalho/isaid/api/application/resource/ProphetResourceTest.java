package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProphetResourceTest {

    private static final String LOGIN = "lcarvalho";

    @Mock
    private ProphetService prophetService;

    @InjectMocks
    private ProphetResource prophetResource;

    @Test
    public void testGetProphet() {

        // Given
        Prophet expectedProphet = buildProphet();
        Mockito.when(prophetService.retrieveProphetBy(Mockito.eq(LOGIN))).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testGetProphetByInvalidLoginParameter() {

        // Given
        String login = null;
        Mockito.when(prophetService.retrieveProphetBy(Mockito.isNull())).thenThrow(new InvalidParameterException());

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophetResource.getProphet(login));
    }

    @Test
    public void testGetProphetByInexistentLogin() {

        // Given
        Prophet expectedProphet = null;
        Mockito.when(prophetService.retrieveProphetBy(LOGIN)).thenReturn(null);

        // When
        Prophet actualProphet = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testCreateProphet() {

        // Given
        Prophet expectedProphet = buildProphet();
        Mockito.when(prophetService.createProphet(Mockito.eq(LOGIN))).thenReturn(expectedProphet);

        // When
        Prophet actualProphet = prophetResource.createProphet(LOGIN);

        // Then
        assertEquals(expectedProphet, actualProphet);
    }

    @Test
    public void testCreateProphetWithInvalidLoginParameter() {

        // Given
        String login = "";
        Mockito.when(prophetService.createProphet(Mockito.eq(Strings.EMPTY))).thenThrow(new InvalidParameterException());

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophetResource.createProphet(login));
    }

    private Prophet buildProphet() {
        return new Prophet(LOGIN, UUID.randomUUID().toString());
    }
}