package com.lcarvalho.isaid.api.application.resource;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
public class ProphetResource {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    ProphetRepository prophetRepository;

    @GetMapping("/prophet/{login}")
    public Prophet getProphet(@PathVariable(value = "login") String login) {
        return prophetRepository.findByLogin(login);
    }

    @PostMapping("/prophet")
    public Prophet insertProphet(@RequestBody String login) {
        return prophetRepository.save(new Prophet(login, UUID.randomUUID().toString()));
    }
}
