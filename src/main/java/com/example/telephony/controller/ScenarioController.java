package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.dto.scenario.ScenarioDto;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.mapper.scneario.ScenarioMapper;
import com.example.telephony.service.ScenarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public Page<ScenarioDto> getAll(@ApiParam("Number page") @RequestParam("page") int page,
                                    @ApiParam("Page size") @RequestParam("size") int size,
                                    @ApiParam("Sort direction")
                                        @RequestParam(value = "direction", required = false, defaultValue = "ASC")
                                        Sort.Direction direction,
                                    @ApiParam("Sort field")
                                        @RequestParam(value = "sortBy", required = false, defaultValue = "NAME")
                                        FieldsPageSort sort,
                                    @ApiParam("Filtering by name")
                                        @RequestParam(value = "name", required = false, defaultValue = "")
                                        String searchedName) {
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
