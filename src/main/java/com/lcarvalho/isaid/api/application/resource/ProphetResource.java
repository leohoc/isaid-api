package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.domain.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.domain.service.ProphetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProphetResource {

    @Autowired
    ProphetService prophetService;

    @GetMapping("/prophets/{login}")
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

    @PostMapping(value = "/prophets", consumes = "application/json")
    public ResponseEntity createProphet(@RequestBody Prophet requestProphet) {
        try {
            Prophet prophet = prophetService.createProphet(requestProphet.getLogin());
            return new ResponseEntity(prophet, HttpStatus.OK);
        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (ProphetAlreadyExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}