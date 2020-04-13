package com.lcarvalho.isaid.api.infrastructure.persistence;

import com.lcarvalho.isaid.api.domain.entity.Prophet;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ProphetRepository extends CrudRepository<Prophet, String> {
    Prophet findByLogin(final String login);
    Prophet findByProphetCode(final String prophetCode);
}
