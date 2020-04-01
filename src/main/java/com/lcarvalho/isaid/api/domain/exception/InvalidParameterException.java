package com.lcarvalho.isaid.api.domain.exception;

public class InvalidParameterException extends Exception {

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException() {
    }
}
