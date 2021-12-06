package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.dto.scenario.ScenarioDto;
import com.example.telephony.mapper.scneario.ScenarioMapper;
import com.example.telephony.service.ScenarioService;
import io.swagger.annotations.ApiOperation;
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
        return scenarioMapper.fromScenario(scenario);
    }

    @PostMapping
    public ScenarioDto create(@RequestParam("name") String scenarioName) {
        return scenarioMapper.fromScenario(scenarioService.create(scenarioName));
    }

    @PutMapping("{id}")
    @ApiOperation("Update scenario. Id retention is not guaranteed")
    public ScenarioDto update(@RequestBody ScenarioDto scenarioDto, @PathVariable("id") Long id) {
        Scenario scenario = scenarioService.update(scenarioMapper.fromScenarioDto(scenarioDto), id);
        return scenarioMapper.fromScenario(scenario);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        scenarioService.delete(id);
    }
}
