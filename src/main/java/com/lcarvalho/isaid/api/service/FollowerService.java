package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.FollowerRequest;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerService {

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private FollowerRepository followerRepository;

    public Follower createFollower(final String prophetLogin, final FollowerRequest followerRequest) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);
        return followerRepository.save(new Follower(prophet.getProphetCode(), followerRequest));
    }

    public List<Follower> getProphetsFollowedBy(String login) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(login);
        return followerRepository.findByFollowerCode(prophet.getProphetCode());
    }

    public List<Follower> getProphetFollowers(String login) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(login);
        return followerRepository.findByProphetCode(prophet.getProphetCode());
    }
}
