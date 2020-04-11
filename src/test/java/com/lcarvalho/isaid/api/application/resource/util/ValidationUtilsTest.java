package com.lcarvalho.isaid.api.application.resource.util;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    public void testValidateLogin() {

        // Given
        String login = "validlogin";

        // When Then
        assertDoesNotThrow(() -> ValidationUtils.validateLogin(login));
    }

    @Test
    public void testValidateEmptyLogin() {

        // Given
        String login = "";

        // When Then
        assertThrows(InvalidParameterException.class, () -> ValidationUtils.validateLogin(login));
    }

    @Test
    public void testValidateNullLogin() {

        // Given
        String login = null;

        // When Then
        assertThrows(InvalidParameterException.class, () -> ValidationUtils.validateLogin(login));
    }

    @Test
    public void testValidateLoginWithBlankSpace() {

        // Given
        String login = "invalid login";

        // When Then
        assertThrows(InvalidParameterException.class, () -> ValidationUtils.validateLogin(login));
    }

    @Test
    public void testValidateLoginWithInvalidCharacter() {

        // Given
        String login = "invalidlogin@123";

        // When Then
        assertThrows(InvalidParameterException.class, () -> ValidationUtils.validateLogin(login));
    }
}