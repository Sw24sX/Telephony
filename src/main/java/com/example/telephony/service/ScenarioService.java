package com.example.telephony.service;

import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.Sound;
import com.example.telephony.dto.SoundDto;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.ScenarioRepository;
import com.example.telephony.repository.SoundRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        scenario.setId(scenarioDb.getId());
        return scenarioRepository.save(scenario);
    }

    public void delete(Long id) {
        Scenario scenario = getById(id);
        scenarioRepository.delete(scenario);
    }
}
