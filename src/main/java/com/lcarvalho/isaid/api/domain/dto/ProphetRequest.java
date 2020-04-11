package com.lcarvalho.isaid.api.domain.dto;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;

import static com.lcarvalho.isaid.api.application.resource.util.ValidationUtils.*;

public class ProphetRequest {

    private String login;

    public ProphetRequest() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void validate() throws InvalidParameterException {
        validateLogin(login);
    }
}
