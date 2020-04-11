package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.ProphetRequest;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.service.ProphetService;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProphetResourceTest {

    private static final String LOGIN = "lskywalker";

    @Mock
    private ProphetService prophetService;

    @InjectMocks
    private ProphetResource prophetResource;

    @Test
    public void testGetProphet() throws ProphetNotFoundException {

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

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(login);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testGetProphetByInexistentLogin() throws ProphetNotFoundException {

        // Given
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(prophetService.retrieveProphetBy(LOGIN)).thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateProphet() throws ProphetAlreadyExistsException {

        // Given
        ProphetRequest prophetRequest = buildProphetRequest(LOGIN);
        Prophet expectedProphet = new Prophet(prophetRequest);
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedProphet, HttpStatus.OK);
        Mockito.when(prophetService.createProphet(Mockito.eq(prophetRequest))).thenReturn(expectedProphet);

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(prophetRequest);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateProphetWithInvalidLoginParameter() {

        // Given
        ProphetRequest prophetRequest = buildProphetRequest(null);
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(prophetRequest);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateAlreadyExistentProphet() throws ProphetAlreadyExistsException {

        // Given
        ProphetRequest prophetRequest = buildProphetRequest(LOGIN);
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        Mockito.when(prophetService.createProphet(Mockito.eq(prophetRequest))).thenThrow(new ProphetAlreadyExistsException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(prophetRequest);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    private Prophet buildProphet() {
        return new Prophet(LOGIN, UUID.randomUUID().toString());
    }

    private ProphetRequest buildProphetRequest(final String login) {
        ProphetRequest prophetRequest = new ProphetRequest();
        prophetRequest.setLogin(login);
        return prophetRequest;
    }
}