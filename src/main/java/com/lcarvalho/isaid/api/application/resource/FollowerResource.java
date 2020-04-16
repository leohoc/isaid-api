package com.lcarvalho.isaid.api.application.resource;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.service.FollowerService;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lcarvalho.isaid.api.application.resource.util.ValidationUtils.*;

@RestController
public class FollowerResource {

    private static final Integer DEFAULT_PAGE = 0;
    private static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FollowerService followerService;

    @PostMapping(value = "/prophets/{login}/followers", consumes = "application/json")
    public ResponseEntity createFollower(@PathVariable("login") final String login,
                                         @RequestBody final FollowerRequest followerRequest) {

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

    @GetMapping(value = "/prophets/{login}/followedProphets")
    public ResponseEntity getFollowedProphets(@PathVariable("login") final String login) {

        LOGGER.info("m=getFollowedProphets, login={}", login);

        try {

            validateLogin(login);

            List<Follower> followedProphets = followerService.getProphetsFollowedBy(login);
            return new ResponseEntity(followedProphets, HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/prophets/{login}/followers")
    public ResponseEntity getProphetFollowers(@PathVariable("login") final String login,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page) {

        LOGGER.info("m=getProphetFollowers, login={}, page={}", login, page);

        try {

            validateLogin(login);

            Page<Follower> followers = followerService.getProphetFollowers(login, page);
            return new ResponseEntity(followers.getContent(), HttpStatus.OK);

        } catch (InvalidParameterException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } catch (ProphetNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
