package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.springframework.util.StringUtils;

public class ProphecyRequest {

    private static final Integer SUMMARY_MAXIMUM_LENGTH = 140;
    private static final Integer DESCRIPTION_MAXIMUM_LENGTH = 280;
    private static final String INVALID_SUMMARY_EXCEPTION_MESSAGE = "summary cannot be null or an empty string and must have a maximum of 140 characters";
    private static final String INVALID_DESCRIPTION_EXCEPTION_MESSAGE = "description cannot be null or an empty string and must have a maximum of 280 characters";

    private String summary;
    private String description;

    public ProphecyRequest () {}

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

        if (StringUtils.isEmpty(summary) || summary.length() > SUMMARY_MAXIMUM_LENGTH) {
            throw new InvalidParameterException(INVALID_SUMMARY_EXCEPTION_MESSAGE);
        }
        if (StringUtils.isEmpty(description) || description.length() > DESCRIPTION_MAXIMUM_LENGTH) {
            throw new InvalidParameterException(INVALID_DESCRIPTION_EXCEPTION_MESSAGE);
        }
    }
}
