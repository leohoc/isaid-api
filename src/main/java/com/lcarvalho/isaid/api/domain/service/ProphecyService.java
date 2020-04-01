package com.lcarvalho.isaid.api.domain.service;

import com.lcarvalho.isaid.api.domain.exception.InvalidParameterException;
import com.lcarvalho.isaid.api.domain.exception.ProphetNotFoundException;
import com.lcarvalho.isaid.api.domain.model.Prophecy;
import com.lcarvalho.isaid.api.domain.model.Prophet;
import com.lcarvalho.isaid.api.infrastructure.persistence.ProphecyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public Prophecy createProphecy(final String prophetLogin, final String summary, final String description) throws ProphetNotFoundException, InvalidParameterException {

        validate(prophetLogin, summary, description);

        Prophet prophet = prophetService.retrieveProphetBy(prophetLogin);

        if (prophet == null) {
            throw new ProphetNotFoundException();
        }

        Prophecy prophecy = new Prophecy(prophet.getProphetCode(), summary, description);
        return prophecyRepository.save(prophecy);
    }

    public List<Prophecy> retrievePropheciesBy(String prophetCode) {
        return prophecyRepository.findByProphetCode(prophetCode);
    }

    private void validate(String prophetLogin, String summary, String description) throws InvalidParameterException {
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
}
