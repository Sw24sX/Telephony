package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.Scenario;
import com.example.telephony.dto.ScenarioDto;
import com.example.telephony.dto.ScenarioStepDto;
import com.example.telephony.dto.scenario.Node;
import com.example.telephony.dto.scenario.ScenarioTestDto;
import com.example.telephony.service.ScenarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "scenario")
public class ScenarioController {
    private final ScenarioService scenarioService;

    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping
    public List<ScenarioDto> getAll() {
        return null;
    }

    @GetMapping("{id}")
    public Node getById(@PathVariable("id") Long id) {
        Scenario scenario = scenarioService.getById(id);
//        return scenarioMapper.scenarioToScenarioDto(scenario);
        return null;
    }

    @GetMapping("{id}/steps")
    public List<ScenarioStepDto> getListStepsDto(@PathVariable("id") Long id) {
        Scenario scenario = scenarioService.getById(id);
//        return scenarioStepEntityMapper.scenarioStepToScenarioStepDtoList(scenario.getFirstStep());
        return null;
    }

    @PostMapping
    public ScenarioTestDto create(@RequestBody ScenarioDto scenarioDto) {
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
