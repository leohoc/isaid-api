package com.lcarvalho.isaid.api.service;

import com.google.common.annotations.VisibleForTesting;
import com.lcarvalho.isaid.api.domain.dto.ProphecyDTO;
import com.lcarvalho.isaid.api.domain.dto.ProphetDTO;
import com.lcarvalho.isaid.api.service.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.service.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.entity.Prophecy;
import com.lcarvalho.isaid.api.domain.entity.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProphecyService {

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private ProphecyRepository prophecyRepository;

    public ProphecyDTO createProphecy(final String prophetLogin, final ProphecyDTO prophecyDTO) throws ProphetNotFoundException, InvalidParameterException {

        ProphetDTO prophet = prophetService.retrieveProphetBy(prophetLogin);
        prophecyDTO.setProphetCode(prophet.getProphetCode());
        prophecyDTO.validate();

        Prophecy prophecy = prophecyRepository.save(new Prophecy(prophecyDTO));
        return new ProphecyDTO(prophecy);
    }

    public List<ProphecyDTO> retrievePropheciesBy(final String prophetLogin, final LocalDateTime startDateTime, final LocalDateTime endDateTime) throws InvalidParameterException, ProphetNotFoundException {

        ProphetDTO prophet = prophetService.retrieveProphetBy(prophetLogin);
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

        return convertToProphecyDTOList(prophecies);
    }

    @VisibleForTesting
    public List<Prophecy> retrievePropheciesBy(final String prophetCode) {
        return prophecyRepository.findByProphetCode(prophetCode);
    }

    private List<ProphecyDTO> convertToProphecyDTOList(List<Prophecy> prophecies) {
        return prophecies.stream().map(ProphecyDTO::new).collect(Collectors.toList());
    }
}
