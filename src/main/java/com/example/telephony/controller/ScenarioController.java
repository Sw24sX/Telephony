package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.ScenarioStepEntity;
import com.example.telephony.dto.ScenarioDto;
import com.example.telephony.mapper.ScenarioMapper;
import com.example.telephony.mapper.ScenarioStepEntityMapper;
import com.example.telephony.service.ScenarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "scenario")
public class ScenarioController {
    private final ScenarioMapper scenarioMapper;
    private final ScenarioService scenarioService;
    private final ScenarioStepEntityMapper scenarioStepEntityMapper;

    public ScenarioController(ScenarioMapper scenarioMapper, ScenarioService scenarioService,
                              ScenarioStepEntityMapper scenarioStepEntityMapper) {
        this.scenarioMapper = scenarioMapper;
        this.scenarioService = scenarioService;
        this.scenarioStepEntityMapper = scenarioStepEntityMapper;
    }

    @GetMapping
    public List<ScenarioDto> getAll() {
        return scenarioMapper.listScenarioToListScenarioDt(scenarioService.getAll());
    }

    @GetMapping("{id}")
    public ScenarioDto getById(@PathVariable("id") Long id) {
        Scenario scenario = scenarioService.getById(id);
        return scenarioMapper.scenarioToScenarioDto(scenario);
    }

    @PostMapping
    public ScenarioDto create(@RequestBody ScenarioDto scenarioDto) {
        Scenario scenario = scenarioMapper.scenarioDtoToScenario(scenarioDto);
        List<ScenarioStepEntity> scenarioStepEntities = scenarioStepEntityMapper.scenarioStepDtoToScenarioStepEntity(scenarioDto.getScenarioStepDtos());
        return scenarioMapper.scenarioToScenarioDto(scenarioService.create(scenario, scenarioStepEntities));
    }

    @PutMapping("{id}")
    public ScenarioDto update(@RequestBody ScenarioDto scenarioDto, @PathVariable("id") Long id) {
        Scenario scenario = scenarioMapper.scenarioDtoToScenario(scenarioDto);
        return scenarioMapper.scenarioToScenarioDto(scenarioService.update(scenario, id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        scenarioService.delete(id);
    }
}
