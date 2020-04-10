package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.ProphetDTO;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.service.ProphetService;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.apache.logging.log4j.util.Strings;
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
    public void testGetProphet() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        ProphetDTO expectedProphet = buildProphet();
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedProphet, HttpStatus.OK);
        Mockito.when(prophetService.retrieveProphetBy(Mockito.eq(LOGIN))).thenReturn(expectedProphet);

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testGetProphetByInvalidLoginParameter() throws InvalidParameterException, ProphetNotFoundException {

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
    public void testGetProphetByInexistentLogin() throws InvalidParameterException, ProphetNotFoundException {

        // Given
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(prophetService.retrieveProphetBy(LOGIN)).thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.getProphet(LOGIN);

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateProphet() throws ProphetAlreadyExistsException, InvalidParameterException {

        // Given
        ProphetDTO expectedProphet = buildProphet();
        ResponseEntity expectedResponseEntity = new ResponseEntity(expectedProphet, HttpStatus.OK);
        Mockito.when(prophetService.createProphet(Mockito.eq(LOGIN))).thenReturn(expectedProphet);

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(new ProphetDTO(LOGIN));

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateProphetWithInvalidLoginParameter() throws ProphetAlreadyExistsException, InvalidParameterException {

        // Given
        String login = "";
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        Mockito.when(prophetService.createProphet(Mockito.eq(Strings.EMPTY))).thenThrow(new InvalidParameterException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(new ProphetDTO(login));

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void testCreateAlreadyExistentProphet() throws ProphetAlreadyExistsException, InvalidParameterException {

        // Given
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        Mockito.when(prophetService.createProphet(Mockito.eq(LOGIN))).thenThrow(new ProphetAlreadyExistsException());

        // When
        ResponseEntity actualResponseEntity = prophetResource.createProphet(new ProphetDTO(LOGIN));

        // Then
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    private ProphetDTO buildProphet() {
        return new ProphetDTO(LOGIN, UUID.randomUUID().toString());
    }
}