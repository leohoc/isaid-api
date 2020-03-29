package com.lcarvalho.isaid.api.domain.service;

import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.UUID;

@Service
public class ProphetService {

    private static final String INVALID_LOGIN_EXCEPTION_MESSAGE = "login cannot be null or an empty string";

    @Autowired
    private ProphetRepository prophetRepository;

    public Prophet createProphet(String login) throws ProphetAlreadyExistsException {
        validate(login);
        if (prophetRepository.findByLogin(login) != null) {
            throw new ProphetAlreadyExistsException();
        }
        return prophetRepository.save(new Prophet(login, UUID.randomUUID().toString()));
    }

    @VisibleForTesting
    public Prophet createProphet(String login, String prophetCode) {
        validate(login);
        return prophetRepository.save(new Prophet(login, prophetCode));
    }

    public Prophet retrieveProphetBy(String login) {
        validate(login);
        return prophetRepository.findByLogin(login);
    }

    private void validate(String login) {
        if (StringUtils.isEmpty(login)) {
            throw new InvalidParameterException(INVALID_LOGIN_EXCEPTION_MESSAGE);
        }
    }
}
