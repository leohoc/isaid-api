package com.lcarvalho.isaid.api.application.resource.util;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.springframework.util.StringUtils;

public class ValidationUtils {

    private static final String INVALID_LOGIN_EXCEPTION_MESSAGE = "login cannot be null or an empty string";

    public static void validateLogin(final String login) throws InvalidParameterException {
        if (StringUtils.isEmpty(login)) {
            throw new InvalidParameterException(INVALID_LOGIN_EXCEPTION_MESSAGE);
        }
    }
}
