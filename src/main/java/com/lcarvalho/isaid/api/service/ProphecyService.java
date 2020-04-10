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

    private static final Integer SUMMARY_MAXIMUM_LENGTH = 140;
    private static final Integer DESCRIPTION_MAXIMUM_LENGTH = 280;
    private static final String INVALID_LOGIN_EXCEPTION_MESSAGE = "prophetLogin cannot be null or an empty string";
    private static final String INVALID_SUMMARY_EXCEPTION_MESSAGE = "summary cannot be null or an empty string and must have a maximum of 140 characters";
    private static final String INVALID_DESCRIPTION_EXCEPTION_MESSAGE = "description cannot be null or an empty string and must have a maximum of 280 characters";

    @Autowired
    private ProphetService prophetService;

    @Autowired
    private ProphecyRepository prophecyRepository;

    public ProphecyDTO createProphecy(final String prophetLogin, final ProphecyDTO prophecyDTO) throws ProphetNotFoundException, InvalidParameterException {

        validate(prophetLogin, prophecyDTO.getSummary(), prophecyDTO.getDescription());
        ProphetDTO prophet = prophetService.retrieveProphetBy(prophetLogin);

        Prophecy prophecy = prophecyRepository.save(new Prophecy(prophet.getProphetCode(), prophecyDTO.getSummary(), prophecyDTO.getDescription()));
        return new ProphecyDTO(prophecy);
    }

    public List<ProphecyDTO> retrievePropheciesBy(final String prophetLogin, final LocalDateTime startDateTime, final LocalDateTime endDateTime) throws InvalidParameterException, ProphetNotFoundException {

        ProphetDTO prophet = prophetService.retrieveProphetBy(prophetLogin);
        List<Prophecy> prophecies = new ArrayList<>();

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

    private void validate(final String prophetLogin, final String summary, final String description) throws InvalidParameterException {
        if (StringUtils.isEmpty(prophetLogin)) {
            throw new InvalidParameterException(INVALID_LOGIN_EXCEPTION_MESSAGE);
        }
        if (StringUtils.isEmpty(summary) || summary.length() > SUMMARY_MAXIMUM_LENGTH) {
            throw new InvalidParameterException(INVALID_SUMMARY_EXCEPTION_MESSAGE);
        }
        if (StringUtils.isEmpty(description) || description.length() > DESCRIPTION_MAXIMUM_LENGTH) {
            throw new InvalidParameterException(INVALID_DESCRIPTION_EXCEPTION_MESSAGE);
        }
    }

    private List<ProphecyDTO> convertToProphecyDTOList(List<Prophecy> prophecies) {
        return prophecies.stream().map(ProphecyDTO::new).collect(Collectors.toList());
    }
}
