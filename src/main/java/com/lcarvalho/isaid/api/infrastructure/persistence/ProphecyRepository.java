package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.ProphecyId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@EnableScan
public interface ProphecyRepository extends CrudRepository<Prophecy, ProphecyId> {
    List<Prophecy> findByProphetCode(final String prophetCode);
    List<Prophecy> findByProphetCodeAndProphecyTimestampBetween(final String prophetCode, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
    List<Prophecy> findByProphetCodeAndProphecyTimestampGreaterThan(final String prophetCode, final LocalDateTime startDateTime);
    List<Prophecy> findByProphetCodeAndProphecyTimestampLessThan(final String prophetCode, final LocalDateTime endDateTime);
}
