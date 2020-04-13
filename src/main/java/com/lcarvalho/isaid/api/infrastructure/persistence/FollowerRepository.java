package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.FollowerId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface FollowerRepository  extends CrudRepository<Follower, FollowerId> {
    List<Follower> findByFollowerCode(String followerCode);
    List<Follower> findByProphetCode(String prophetCode);
}
