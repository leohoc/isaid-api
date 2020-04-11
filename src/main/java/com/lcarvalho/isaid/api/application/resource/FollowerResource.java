package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.service.FollowerService;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.lcarvalho.isaid.api.application.resource.util.ValidationUtils.*;

@RestController
public class FollowerResource {

    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FollowerService followerService;

    @PostMapping(value = "/prophets/{login}/followers", consumes = "application/json")
    public ResponseEntity createFollower(@PathVariable("login") String login,
                                         @RequestBody FollowerRequest followerRequest) {

        LOGGER.info("m=createFollower, login={}, followerCode={}", login, followerRequest.getFollowerCode());

        try {

            validateLogin(login);
            followerRequest.validate();

            Follower follower = followerService.createFollower(login, followerRequest);
            return new ResponseEntity(follower, HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
