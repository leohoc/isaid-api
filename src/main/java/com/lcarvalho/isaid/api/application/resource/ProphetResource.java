package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
public class ProphetResource {

    @Autowired
    ProphetService prophetService;

    @GetMapping("/prophet/{login}")
    public ResponseEntity getProphet(@PathVariable(value = "login") String login) {
        try {
            Prophet prophet = prophetService.retrieveProphetBy(login);
            if (prophet != null) {
                return new ResponseEntity(prophet, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/prophet")
    public ResponseEntity createProphet(@RequestBody String login) throws ProphetAlreadyExistsException {
        try {
            Prophet prophet = prophetService.createProphet(login);
            return new ResponseEntity(prophet, HttpStatus.OK);
        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (ProphetAlreadyExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}