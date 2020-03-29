package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedProphet, HttpStatus.OK);
        Mockito.when(prophetService.retrieveProphetBy(Mockito.eq(LOGIN))).thenReturn(expectedProphet);

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testGetProphetByInvalidLoginParameter() {

        // Given
        String login = null;
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        Mockito.when(prophetService.retrieveProphetBy(Mockito.isNull())).thenThrow(new InvalidParameterException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(login);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testGetProphetByInexistentLogin() {

        // Given
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(prophetService.retrieveProphetBy(LOGIN)).thenReturn(null);

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateProphet() throws ProphetAlreadyExistsException {

        // Given
        Prophet expectedProphet = buildProphet();
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedProphet, HttpStatus.OK);
        Mockito.when(prophetService.createProphet(Mockito.eq(LOGIN))).thenReturn(expectedProphet);

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateProphetWithInvalidLoginParameter() throws ProphetAlreadyExistsException {

        // Given
        String login = "";
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        Mockito.when(prophetService.createProphet(Mockito.eq(Strings.EMPTY))).thenThrow(new InvalidParameterException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(login);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateAlreadyExistentProphet() throws ProphetAlreadyExistsException {

        // Given
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        Mockito.when(prophetService.createProphet(Mockito.eq(LOGIN))).thenThrow(new ProphetAlreadyExistsException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    private Prophet buildProphet() {
        return new Prophet(LOGIN, UUID.randomUUID().toString());
    }
}