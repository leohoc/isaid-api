package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.service.ProphecyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProphecyResourceTest {

    private static final String PROPHET_CODE = "8104fb98-d168-42ba-8244-fed780e68279";

    @Mock
    private ProphecyService prophecyService;

    @InjectMocks
    private ProphecyResource prophecyResource;

    @Test
    public void testCreateProphet() throws ProphetNotFoundException, InvalidParameterException {

        // Given
        String prophetLogin = "hsolo";
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        Prophecy expectedProphecy = buildProphecy(summary, description);

        Mockito.when(prophecyService
                        .createProphecy(Mockito.eq(prophetLogin), Mockito.eq(summary), Mockito.eq(description)))
                        .thenReturn(expectedProphecy);

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));

        // Then
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
        assertEquals(expectedProphecy.getProphetCode(), ((Prophecy)actualResponseEntity.getBody()).getProphetCode());
        assertEquals(expectedProphecy.getSummary(), ((Prophecy)actualResponseEntity.getBody()).getSummary());
        assertEquals(expectedProphecy.getDescription(), ((Prophecy)actualResponseEntity.getBody()).getDescription());
    }

    @Test
    public void testCreateProphetWithInvalidParameter() throws ProphetNotFoundException, InvalidParameterException {

        // Given
        String prophetLogin = "hsolo";
        String summary = null;
        String description = null;

        Mockito.when(prophecyService
                .createProphecy(Mockito.eq(prophetLogin), Mockito.eq(summary), Mockito.eq(description)))
                .thenThrow(new InvalidParameterException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    @Test
    public void testCreateProphetWithNonexistentProphetLogin() throws ProphetNotFoundException, InvalidParameterException {

        // Given
        String prophetLogin = "kj12i31i2u3h98y";
        String summary = "Prophecy summary";
        String description = "Prophecy description";

        Mockito.when(prophecyService
                .createProphecy(Mockito.eq(prophetLogin), Mockito.eq(summary), Mockito.eq(description)))
                .thenThrow(new ProphetNotFoundException());

        // When
        ResponseEntity actualResponseEntity = prophecyResource.createProphecy(prophetLogin, new Prophecy(null, null, summary, description));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
        assertNull(actualResponseEntity.getBody());
    }

    private Prophecy buildProphecy(String summary, String description) {
        return new Prophecy(PROPHET_CODE, summary, description);
    }

}