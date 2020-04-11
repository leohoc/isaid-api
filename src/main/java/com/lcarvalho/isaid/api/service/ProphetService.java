package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.ProphetRequest;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProphetService {

    @Autowired
    private ProphetRepository prophetRepository;

    public Prophet createProphet(final ProphetRequest prophetRequest) throws ProphetAlreadyExistsException {

        if (prophetRepository.findByLogin(prophetRequest.getLogin()) != null) {
            throw new ProphetAlreadyExistsException();
        }

        return prophetRepository.save(new Prophet(prophetRequest));
    }

    public Prophet retrieveProphetBy(final String login) throws ProphetNotFoundException {

        Prophet prophet = prophetRepository.findByLogin(login);
        if (prophet == null) {
            throw new ProphetNotFoundException();
        }
        return prophet;
    }
}
