package com.lcarvalho.isaid.api.domain.service;

import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProphetService {

    @Autowired
    private ProphetRepository prophetRepository;

    public Prophet createProphet(String login) {
        return prophetRepository.save(new Prophet(login, UUID.randomUUID().toString()));
    }

    public Prophet retrieveProphetBy(String login) {
        return prophetRepository.findByLogin(login);
    }
}
