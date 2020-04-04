package com.lcarvalho.isaid.api.service;

import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.ProphetDTO;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class ProphetService {

    private static final String INVALID_LOGIN_EXCEPTION_MESSAGE = "login cannot be null or an empty string";

    @Autowired
    private ProphetRepository prophetRepository;

    public ProphetDTO createProphet(String login) throws ProphetAlreadyExistsException, InvalidParameterException {
        validate(login);
        if (prophetRepository.findByLogin(login) != null) {
            throw new ProphetAlreadyExistsException();
        }
        Prophet prophet = prophetRepository.save(new Prophet(login, UUID.randomUUID().toString()));
        return new ProphetDTO(prophet);
    }

    @VisibleForTesting
    public Prophet createProphet(String login, String prophetCode) throws InvalidParameterException {
        validate(login);
        return prophetRepository.save(new Prophet(login, prophetCode));
    }

    public ProphetDTO retrieveProphetBy(String login) throws InvalidParameterException {
        validate(login);
        Prophet prophet = prophetRepository.findByLogin(login);
        return prophet == null ? null : new ProphetDTO(prophet);
    }

    private void validate(String login) throws InvalidParameterException {
        if (StringUtils.isEmpty(login)) {
            throw new InvalidParameterException(INVALID_LOGIN_EXCEPTION_MESSAGE);
        }
    }
}
