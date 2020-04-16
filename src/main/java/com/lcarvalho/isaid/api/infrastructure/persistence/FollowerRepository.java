package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.FollowerId;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EnableScan
public interface FollowerRepository extends DynamoDBPagingAndSortingRepository<Follower, FollowerId> {
    List<Follower> findByFollowerCode(String followerCode);
    Page<Follower> findByProphetCode(String prophetCode, Pageable pageRequest);
}
