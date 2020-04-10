package com.lcarvalho.isaid.api.service;

import com.lcarvalho.isaid.api.domain.dto.FollowerDTO;
import com.lcarvalho.isaid.api.domain.dto.ProphetDTO;
import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.infrastructure.persistence.FollowerRepository;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FollowerService {

    private static final String INVALID_FOLLOWER_CODE_MESSSAGE = "followerCode cannot be null or an empty string";
    @Autowired
    private ProphetService prophetService;

    @Autowired
    private FollowerRepository followerRepository;

    public FollowerDTO createFollower(final String prophetLogin, final FollowerDTO followerDTO) throws InvalidParameterException, ProphetNotFoundException {

        validate(followerDTO.getFollowerCode());

        ProphetDTO prophet = prophetService.retrieveProphetBy(prophetLogin);
        Follower follower = followerRepository.save(new Follower(followerDTO.getFollowerCode(), prophet.getProphetCode()));
        return new FollowerDTO(follower);
    }

    private void validate(String followerCode) throws InvalidParameterException {
        if (StringUtils.isEmpty(followerCode)) {
            throw new InvalidParameterException(INVALID_FOLLOWER_CODE_MESSSAGE);
        }
    }
}
