package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.service.ProphecyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

@RestController
public class ProphecyResource {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphecyService prophecyService;

    @PostMapping("/prophets/{login}/prophecies")
    public ResponseEntity createProphecy(@PathVariable(value = "login") String login,
                                         @RequestBody String summary,
                                         @RequestBody String description) throws ProphetNotFoundException {

        LOGGER.info("m=createProphecy, login={}, summary={}, description={}", login, summary, description);

        try {
            Prophecy prophecy = prophecyService.createProphecy(login, summary, description);
            return new ResponseEntity(prophecy, HttpStatus.OK);
        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
