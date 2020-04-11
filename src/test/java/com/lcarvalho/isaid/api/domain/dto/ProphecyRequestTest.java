package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProphecyRequestTest {

    @Test
    public void testValidateEmptySummary() {

        // Given
        String summary = "";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyRequest.validate());
    }

    @Test
    public void testValidateNullSummary() {

        // Given
        String summary = null;
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyRequest.validate());
    }

    @Test
    public void testValidateLenghtySummary() {

        // Given
        String summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tincidunt metus sit amet elit bibendum euismod. Curabitur rutrum lacinia amet.";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyRequest.validate());
    }

    @Test
    public void testValidateEmptyDescription() {

        // Given
        String summary = "Prophecy summary";
        String description = "";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyRequest.validate());
    }

    @Test
    public void testValidateNullDescription() {

        // Given
        String summary = "Prophecy summary";
        String description = null;
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyRequest.validate());
    }

    @Test
    public void testValidateLenghtyDescription() {

        // Given
        String summary = "Prophecy summary";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan imperdiet neque, in pellentesque " +
                "turpis suscipit id. Sed a pharetra nulla, vel porta ipsum. Integer nec nibh magna. Nunc interdum consectetur aliquet. " +
                "Maecenas gravida commodo lectus non rutrum. Nam cras amet. ";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyRequest.validate());
    }

    @Test
    public void testValidate() throws InvalidParameterException {

        // Given
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyRequest prophecyRequest = buildProphecyRequest(summary, description);

        // When Then
        assertDoesNotThrow(() -> prophecyRequest.validate());
    }

    private ProphecyRequest buildProphecyRequest(final String summary, final String description) {
        ProphecyRequest prophecyRequest = new ProphecyRequest();
        prophecyRequest.setSummary(summary);
        prophecyRequest.setDescription(description);
        return prophecyRequest;
    }
}