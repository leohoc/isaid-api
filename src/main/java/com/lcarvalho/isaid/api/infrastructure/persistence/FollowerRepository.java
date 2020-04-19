package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.entity.Follower;
import com.lcarvalho.isaid.api.domain.entity.FollowerId;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@EnableScan
public interface FollowerRepository extends DynamoDBPagingAndSortingRepository<Follower, FollowerId> {
    Page<Follower> findByFollowerCode(String followerCode, Pageable pageRequest);
    Page<Follower> findByProphetCode(String prophetCode, Pageable pageRequest);
}
