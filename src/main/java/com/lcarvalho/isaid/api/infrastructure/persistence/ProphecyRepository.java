package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.ProphecyId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

@EnableScan
public interface ProphecyRepository extends CrudRepository<Prophecy, ProphecyId> {
    Page<Prophecy> findByProphetCode(final String prophetCode, Pageable pageRequest);
    Page<Prophecy> findByProphetCodeAndProphecyTimestampBetween(final String prophetCode, final LocalDateTime startDateTime, final LocalDateTime endDateTime, Pageable pageRequest);
    Page<Prophecy> findByProphetCodeAndProphecyTimestampGreaterThan(final String prophetCode, final LocalDateTime startDateTime, Pageable pageRequest);
    Page<Prophecy> findByProphetCodeAndProphecyTimestampLessThan(final String prophetCode, final LocalDateTime endDateTime, Pageable pageRequest);
}
