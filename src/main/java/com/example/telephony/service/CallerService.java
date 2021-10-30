package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Scenario;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.CallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallerService {
    private final CallerRepository callerRepository;
    private final ScenarioService scenarioService;
    private final AsteriskService asteriskService;

    @Autowired
    public CallerService(CallerRepository callerRepository, ScenarioService scenarioService, AsteriskService asteriskService) {
        this.callerRepository = callerRepository;
        this.scenarioService = scenarioService;
        this.asteriskService = asteriskService;
    }

    public List<Caller> getAll() {
        return callerRepository.findAll();
    }

    public Caller getById(Long id) {
        return callerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.CALLER_NOT_FOUND.getMessage(), id)));
    }

    public Caller create(Caller caller) {
        return callerRepository.save(caller);
    }

    public List<Caller> create(List<Caller> callers) {
        return callerRepository.saveAll(callers);
    }

    public Caller update(Long id, Caller caller) {
        Caller callerDb = getById(id);
        caller.setId(callerDb.getId());
        return callerRepository.save(caller);
    }

    public void delete(Long id) {
        Caller caller = getById(id);
        callerRepository.delete(caller);
    }

    public void callToCallerWithScenario(Long callerId, Long scenarioId) {
        Caller caller = getById(callerId);
        Scenario scenario = scenarioService.getById(scenarioId);
        asteriskService.callByCallerWithScenario(caller, scenario);
    }
}
