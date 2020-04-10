package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.UUID;

public class ProphecyDTO {

    private static final ZoneId SAO_PAULO_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    private static final Integer SUMMARY_MAXIMUM_LENGTH = 140;
    private static final Integer DESCRIPTION_MAXIMUM_LENGTH = 280;
    private static final String INVALID_PROPHECY_TIMESTAMP_EXCEPTION_MESSAGE = "prophetTimestamp cannot be null";
    private static final String INVALID_PROPHET_CODE_EXCEPTION_MESSAGE = "prophetCode cannot be null or an empty string";
    private static final String INVALID_SUMMARY_EXCEPTION_MESSAGE = "summary cannot be null or an empty string and must have a maximum of 140 characters";
    private static final String INVALID_DESCRIPTION_EXCEPTION_MESSAGE = "description cannot be null or an empty string and must have a maximum of 280 characters";

    private String prophetCode;
    private LocalDateTime prophecyTimestamp = LocalDateTime.now(SAO_PAULO_ZONE_ID);
    private String summary;
    private String description;

    public ProphecyDTO() {}

    public ProphecyDTO(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

    public ProphecyDTO(String prophetCode, LocalDateTime prophecyTimestamp, String summary, String description) {
        this.prophetCode = prophetCode;
        this.prophecyTimestamp = prophecyTimestamp;
        this.summary = summary;
        this.description = description;
    }

    public ProphecyDTO(Prophecy prophecy) {
        this.prophetCode = prophecy.getProphetCode();
        this.prophecyTimestamp = prophecy.getProphecyTimestamp();
        this.summary = prophecy.getSummary();
        this.description = prophecy.getDescription();
    }

    public String getProphetCode() {
        return prophetCode;
    }

    public void setProphetCode(String prophetCode) {
        this.prophetCode = prophetCode;
    }

    public LocalDateTime getProphecyTimestamp() {
        return prophecyTimestamp;
    }

    public void setProphecyTimestamp(LocalDateTime prophecyTimestamp) {
        this.prophecyTimestamp = prophecyTimestamp;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void validate() throws InvalidParameterException {

        validateUUID(prophetCode);

        if (prophecyTimestamp == null) {
            throw new InvalidParameterException(INVALID_PROPHECY_TIMESTAMP_EXCEPTION_MESSAGE);
        }

        if (StringUtils.isEmpty(summary) || summary.length() > SUMMARY_MAXIMUM_LENGTH) {
            throw new InvalidParameterException(INVALID_SUMMARY_EXCEPTION_MESSAGE);
        }
        if (StringUtils.isEmpty(description) || description.length() > DESCRIPTION_MAXIMUM_LENGTH) {
            throw new InvalidParameterException(INVALID_DESCRIPTION_EXCEPTION_MESSAGE);
        }
    }

    private void validateUUID(String uuid) throws InvalidParameterException {
        if (StringUtils.isEmpty(uuid)) {
            throw new InvalidParameterException(INVALID_PROPHET_CODE_EXCEPTION_MESSAGE);
        }
        UUID.fromString(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProphecyDTO that = (ProphecyDTO) o;
        return Objects.equals(prophetCode, that.prophetCode) &&
                Objects.equals(prophecyTimestamp, that.prophecyTimestamp) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prophetCode, prophecyTimestamp, summary, description);
    }
}
