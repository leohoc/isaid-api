package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.service.ProphecyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProphecyResource {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProphecyService prophecyService;

    @PostMapping(value = "/prophets/{login}/prophecies", consumes = "application/json")
    public ResponseEntity createProphecy(@PathVariable("login") String login,
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
    public ResponseEntity getPropheciesBy(@PathVariable("login") final String login,
                                          @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime startDateTime,
                                          @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime endDateTime) {

        LOGGER.info("m=getPropheciesBy, login={}, startDateTime={}, endDateTime={}", login, startDateTime, endDateTime);

        try {

            List<Prophecy> prophecies = prophecyService.retrievePropheciesBy(login, startDateTime, endDateTime);
            return new ResponseEntity(prophecies, HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
