package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.model.ProphecyId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@EnableScan
public interface ProphecyRepository extends CrudRepository<Prophecy, ProphecyId> {
    List<Prophecy> findByProphetCode(String prophetCode);
    List<Prophecy> findByProphetCodeAndProphecyTimestampBetween(String prophetCode, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Prophecy> findByProphetCodeAndProphecyTimestampGreaterThan(String prophetCode, LocalDateTime startDateTime);
    List<Prophecy> findByProphetCodeAndProphecyTimestampLessThan(String prophetCode, LocalDateTime endDateTime);
}
