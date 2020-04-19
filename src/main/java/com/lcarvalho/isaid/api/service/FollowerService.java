package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FollowerService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private FollowerRepository followerRepository;

    public Follower createFollower(final String prophetLogin, final FollowerRequest followerRequest) throws ProphetNotFoundException {
        Prophet followerProphet = prophetService.retrieveProphetBy(UUID.fromString(followerRequest.getFollowerCode()));
        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);
        return followerRepository.save(new Follower(followerProphet.getProphetCode(), prophet.getProphetCode()));
    }

    public Page<Follower> getProphetsFollowedBy(final String login, final Integer page) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(login);
        return followerRepository.findByFollowerCode(prophet.getProphetCode(), buildPageRequest(page));
    }

    public Page<Follower> getProphetFollowers(final String login, final Integer page) throws ProphetNotFoundException {
        Prophet prophet = prophetService.retrieveProphetBy(login);
        return followerRepository.findByProphetCode(prophet.getProphetCode(), buildPageRequest(page));
    }

    private Pageable buildPageRequest(final Integer page) {
        return PageRequest.of(page, PAGE_SIZE);
    }
}
