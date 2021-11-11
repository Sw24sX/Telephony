package com.example.telephony.mapper;

import com.example.telephony.domain.Scenario;
import com.example.telephony.dto.ScenarioDto;
import com.example.telephony.service.SoundService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SoundMapper.class})
public abstract class ScenarioMapper {
    @Autowired
    protected SoundMapper soundMapper;

    @Autowired
    protected SoundService soundService;

    @Mapping(target = "scenarioStepDtos", ignore = true)
    public abstract ScenarioDto scenarioToScenarioDto(Scenario scenario);

    public abstract List<ScenarioDto> listScenarioToListScenarioDt(List<Scenario> scenarios);

    @Mapping(target = "id", ignore = true)
    public abstract Scenario scenarioDtoToScenario(ScenarioDto scenarioDto);
}
