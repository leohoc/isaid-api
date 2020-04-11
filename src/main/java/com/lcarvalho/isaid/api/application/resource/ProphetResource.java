package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.ProphetRequest;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.service.ProphetService;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lcarvalho.isaid.api.application.resource.util.ValidationUtils.*;

@RestController
public class ProphetResource {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    ProphetService prophetService;

    @GetMapping("/prophets/{login}")
    public ResponseEntity getProphet(@PathVariable(value = "login") String login) {

        LOGGER.info("m=getProphet, login={}", login);
        try {

            validateLogin(login);
            Prophet prophet = prophetService.retrieveProphetBy(login);
            return new ResponseEntity(prophet, HttpStatus.OK);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/prophets", consumes = "application/json")
    public ResponseEntity createProphet(@RequestBody ProphetRequest prophetRequest) {

        LOGGER.info("m=createProphet, login={}", prophetRequest.getLogin());

        try {

            prophetRequest.validate();
            Prophet prophet = prophetService.createProphet(prophetRequest);
            return new ResponseEntity(prophet, HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetAlreadyExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}