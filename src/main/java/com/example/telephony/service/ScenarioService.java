package com.example.telephony.service;

import com.example.telephony.domain.Scenario;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.ScenarioRepository;
import com.example.telephony.service.scenario.ScenarioCall;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;

    public ScenarioService(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public List<Scenario> getAll() {
        return scenarioRepository.findAll();
    }

    public Scenario getById(Long id) {
        Scenario scenario = scenarioRepository.findById(id).orElse(null);
        if(scenario == null) {
            throw new EntityNotFoundException(String.format(ExceptionMessage.SCENARIO_NOT_FOUND.getMessage(), id));
        }

        return scenario;
    }

    public Scenario create(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    public Scenario update(Scenario scenario, Long id) {
        Scenario scenarioDb = getById(id);
        scenario.setId(scenario.getId());
        return scenarioRepository.save(scenario);
    }

    public void delete(Long id) {
        Scenario scenario = getById(id);
        scenarioRepository.delete(scenario);
    }
}
