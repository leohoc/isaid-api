package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.service.ProphecyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProphecyResource {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphecyService prophecyService;

    @PostMapping(value = "/prophets/{login}/prophecies", consumes = "application/json")
    public ResponseEntity createProphecy(@PathVariable(value = "login") String login,
                                         @RequestBody Prophecy requestProphecy) {

        LOGGER.info("m=createProphecy, login={}, summary={}, description={}", login, requestProphecy.getSummary(), requestProphecy.getDescription());

        try {

            Prophecy prophecy = prophecyService.createProphecy(login, requestProphecy.getSummary(), requestProphecy.getDescription());
            return new ResponseEntity(prophecy, HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/prophets/{login}/prophecies")
    public ResponseEntity getPropheciesBy(@PathVariable(value = "login") final String login) {

        LOGGER.info("m=getPropheciesBy, login={}", login);

        try {

            List<Prophecy> prophecies = prophecyService.retrievePropheciesByProphetLogin(login);
            return new ResponseEntity(prophecies, HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
