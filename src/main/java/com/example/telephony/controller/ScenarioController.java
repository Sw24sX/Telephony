package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.Scenario;
import com.example.telephony.dto.ScenarioDto;
import com.example.telephony.dto.ScenarioNodeDto;
import com.example.telephony.mapper.ScenarioMapper;
import com.example.telephony.service.ScenarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "scenario")
public class ScenarioController {
    private final ScenarioService scenarioService;
    private final ScenarioMapper scenarioMapper;

    public ScenarioController(ScenarioService scenarioService, ScenarioMapper scenarioMapper) {
        this.scenarioService = scenarioService;
        this.scenarioMapper = scenarioMapper;
    }

    @GetMapping
    public List<ScenarioDto> getAll() {
        return null;
    }

    @GetMapping("{id}")
    public ScenarioDto getById(@PathVariable("id") Long id) {
        Scenario scenario = scenarioService.getById(id);
//        return scenarioMapper.scenarioToScenarioDto(scenario);
        return null;
    }

    @GetMapping("{id}/steps")
    public List<ScenarioNodeDto> getListStepsDto(@PathVariable("id") Long id) {
        Scenario scenario = scenarioService.getById(id);
//        return scenarioStepEntityMapper.scenarioStepToScenarioStepDtoList(scenario.getFirstStep());
        return null;
    }

    @PostMapping
    public ScenarioDto create(@RequestBody ScenarioDto scenarioDto) {
        Scenario scenario = scenarioMapper.fromScenarioDto(scenarioDto);
        return null;
    }

    @PutMapping("{id}")
    public ScenarioDto update(@RequestBody ScenarioDto scenarioDto, @PathVariable("id") Long id) {
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        scenarioService.delete(id);
    }
}
