package com.lcarvalho.isaid.api.application.resource.util;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String INVALID_LOGIN_EXCEPTION_MESSAGE = "login cannot be null, an empty string or have invalid characters (~, #, @, *, +, %, {, }, <, >, [, ], |, \\, _, \", ^, ')";
    private static final Pattern LOGIN_PATTERN = Pattern.compile("[ ~#@*+%{}<>\\[\\]|\"\\_'^]");

    public static void validateLogin(final String login) throws InvalidParameterException {

        if (StringUtils.isEmpty(login) || LOGIN_PATTERN.matcher(login).find()) {
            throw new InvalidParameterException(INVALID_LOGIN_EXCEPTION_MESSAGE);
        }
    }

    public static void validateUUID(String uuid) throws IllegalArgumentException {
        UUID.fromString(uuid);
    }
}
