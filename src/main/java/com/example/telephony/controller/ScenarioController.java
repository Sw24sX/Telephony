package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.dto.scenario.ScenarioDto;
import com.example.telephony.dto.scenario.ScenarioHeaderDto;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.mapper.scneario.ScenarioHeaderMapper;
import com.example.telephony.mapper.scneario.ScenarioMapper;
import com.example.telephony.service.ScenarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(GlobalMapping.API + "scenario")
@Api("Operations pertaining to scenario")
public class ScenarioController {
    private final ScenarioService scenarioService;
    private final ScenarioMapper scenarioMapper;
    private final ScenarioHeaderMapper scenarioHeaderMapper;

    public ScenarioController(ScenarioService scenarioService, ScenarioMapper scenarioMapper,
                              ScenarioHeaderMapper scenarioHeaderMapper) {
        this.scenarioService = scenarioService;
        this.scenarioMapper = scenarioMapper;
        this.scenarioHeaderMapper = scenarioHeaderMapper;
    }

    @GetMapping
    @ApiOperation("Get scenarios headers by pages")
    public Page<ScenarioHeaderDto> getAll(@ApiParam("Number page") @RequestParam("page") int page,
                                          @ApiParam("Page size") @RequestParam("size") int size,
                                          @ApiParam("Sort direction")
                                        @RequestParam(value = "direction", required = false, defaultValue = "ASC")
                                        Sort.Direction direction,
                                          @ApiParam("Sort field")
                                        @RequestParam(value = "sortBy", required = false, defaultValue = "CREATION_DATE")
                                        FieldsPageSort sort,
                                          @ApiParam("Filtering by name")
                                        @RequestParam(value = "name", required = false, defaultValue = "")
                                        String searchedName) {
        return scenarioService.getAll(page, size, sort, direction, searchedName)
                .map(scenarioHeaderMapper::fromScenarioHeader);
    }

    @GetMapping("{id}")
    @ApiOperation("Get scenario by id")
    public ScenarioDto getById(@PathVariable("id") Long id) {
        Scenario scenario = scenarioService.getById(id);
        return scenarioMapper.fromScenario(scenario);
    }

    @PostMapping
    @ApiOperation("Create scenario pattern (start, simple replica, finish)")
    public ScenarioDto create(@RequestParam("name") String scenarioName) {
        return scenarioMapper.fromScenario(scenarioService.create(scenarioName));
    }

    @PutMapping("{id}")
    @ApiOperation("Update scenario (deleting old and creating new structure)")
    public ScenarioDto update(@RequestBody ScenarioDto scenarioDto, @PathVariable("id") Long id) {
        Scenario scenario = scenarioService.update(scenarioMapper.fromScenarioDto(scenarioDto), id);
        return scenarioMapper.fromScenario(scenario);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete scenario by id")
    public void delete(@PathVariable("id") Long id) {
        scenarioService.delete(id);
    }
}
