package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProphetRequestTest {

    private static final String LOGIN = "login";
    private static final String INVALID_LOGIN = "";

    @Test
    public void testValidate() {

        // Given
        ProphetRequest prophetRequest = new ProphetRequest();
        prophetRequest.setLogin(LOGIN);

        // When Then
        assertDoesNotThrow(() -> prophetRequest.validate());
    }

    @Test
    public void testValidateWIthInvalidLogin() {

        // Given
        ProphetRequest prophetRequest = new ProphetRequest();
        prophetRequest.setLogin(INVALID_LOGIN);

        // When Then
        assertThrows(InvalidParameterException.class, () -> prophetRequest.validate());
    }

}