package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.ProphetDTO;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetAlreadyExistsException;
import com.lcarvalho.isaid.api.service.ProphetService;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
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
            ProphetDTO prophet = prophetService.retrieveProphetBy(login);
            return new ResponseEntity(prophet, HttpStatus.OK);
        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/prophets", consumes = "application/json")
    public ResponseEntity createProphet(@RequestBody ProphetDTO requestProphet) {
        try {
            ProphetDTO prophet = prophetService.createProphet(requestProphet.getLogin());
            return new ResponseEntity(prophet, HttpStatus.OK);
        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (ProphetAlreadyExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}