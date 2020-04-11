package com.lcarvalho.isaid.api.service;

import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.ProphecyRequest;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProphecyService {

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private ProphecyRepository prophecyRepository;

    public Prophecy createProphecy(final String prophetLogin, final ProphecyRequest prophecyRequest) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);

        return prophecyRepository.save(new Prophecy(prophet.getProphetCode(), prophecyRequest));
    }

    public List<Prophecy> retrievePropheciesBy(final String prophetLogin, final LocalDateTime startDateTime, final LocalDateTime endDateTime) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);
        List<Prophecy> prophecies;

        if (startDateTime != null && endDateTime != null) {
            prophecies = prophecyRepository.findByProphetCodeAndProphecyTimestampBetween(prophet.getProphetCode(), startDateTime, endDateTime);

        } else if (startDateTime != null) {
            prophecies = prophecyRepository.findByProphetCodeAndProphecyTimestampGreaterThan(prophet.getProphetCode(), startDateTime);

        } else if (endDateTime != null) {
            prophecies = prophecyRepository.findByProphetCodeAndProphecyTimestampLessThan(prophet.getProphetCode(), endDateTime);

        } else {
            prophecies = prophecyRepository.findByProphetCode(prophet.getProphetCode());
        }

        return prophecies;
    }

    @VisibleForTesting
    public List<Prophecy> retrievePropheciesBy(final String prophetCode) {
        return prophecyRepository.findByProphetCode(prophetCode);
    }
}
