package com.lcarvalho.isaid.api.service;

import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.ProphecyRequest;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProphecyService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private ProphecyRepository prophecyRepository;

    public Prophecy createProphecy(final String prophetLogin, final ProphecyRequest prophecyRequest) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);

        return prophecyRepository.save(new Prophecy(prophet.getProphetCode(), prophecyRequest));
    }

    public Page<Prophecy> retrievePropheciesBy(final String prophetLogin, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final Integer page) throws ProphetNotFoundException {

        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);
        Page<Prophecy> prophecies;

        if (startDateTime != null && endDateTime != null) {
            prophecies = prophecyRepository.findByProphetCodeAndProphecyTimestampBetween(prophet.getProphetCode(), startDateTime, endDateTime, buildPageRequest(page));

        } else if (startDateTime != null) {
            prophecies = prophecyRepository.findByProphetCodeAndProphecyTimestampGreaterThan(prophet.getProphetCode(), startDateTime, buildPageRequest(page));

        } else if (endDateTime != null) {
            prophecies = prophecyRepository.findByProphetCodeAndProphecyTimestampLessThan(prophet.getProphetCode(), endDateTime, buildPageRequest(page));

        } else {
            prophecies = prophecyRepository.findByProphetCode(prophet.getProphetCode(), buildPageRequest(page));
        }

        return prophecies;
    }

    @VisibleForTesting
    public Page<Prophecy> retrievePropheciesBy(final String prophetCode) {
        return prophecyRepository.findByProphetCode(prophetCode, buildPageRequest(0));
    }

    private Pageable buildPageRequest(final Integer page) {
        return PageRequest.of(page, PAGE_SIZE);
    }
}
