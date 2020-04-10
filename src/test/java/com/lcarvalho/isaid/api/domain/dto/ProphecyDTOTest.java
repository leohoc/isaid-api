package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProphecyDTOTest {

    @Test
    public void testValidateInvalidProphetCode() {

        // Given
        String prophetCode = "aalksjdad9sa0ds80a8sd0a8d0a";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                IllegalArgumentException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateEmptyProphetCode() {

        // Given
        String prophetCode = "";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateNullProphetCode() {

        // Given
        String prophetCode = null;
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateNullProphecyTimestamp() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = null;
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateEmptySummary() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateNullSummary() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = null;
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateLenghtySummary() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tincidunt metus sit amet elit bibendum euismod. Curabitur rutrum lacinia amet.";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateEmptyDescription() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = "";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateNullDescription() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = null;
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidateLenghtyDescription() {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan imperdiet neque, in pellentesque " +
                "turpis suscipit id. Sed a pharetra nulla, vel porta ipsum. Integer nec nibh magna. Nunc interdum consectetur aliquet. " +
                "Maecenas gravida commodo lectus non rutrum. Nam cras amet. ";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertThrows(
                InvalidParameterException.class,
                () -> prophecyDTO.validate());
    }

    @Test
    public void testValidate() throws InvalidParameterException {

        // Given
        String prophetCode = "7dc93f38-7b51-40de-b7a6-49f2c4bf9e69";
        LocalDateTime prophecyTimestamp = LocalDateTime.now();
        String summary = "Prophecy summary";
        String description = "Prophecy description";
        ProphecyDTO prophecyDTO = new ProphecyDTO(prophetCode, prophecyTimestamp, summary, description);

        // When Then
        assertDoesNotThrow(() -> prophecyDTO.validate());
    }

}