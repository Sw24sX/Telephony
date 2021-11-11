package com.example.telephony.mapper;

import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.ScenarioStepEntity;
import com.example.telephony.domain.Sound;
import com.example.telephony.dto.ScenarioDto;
import com.example.telephony.dto.ScenarioStepDto;
import com.example.telephony.dto.SoundDto;
import com.example.telephony.service.SoundService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SoundMapper.class})
public abstract class ScenarioMapper {
    @Autowired
    protected SoundMapper soundMapper;

    @Autowired
    protected SoundService soundService;

    public abstract ScenarioDto scenarioToScenarioDto(Scenario scenario);

    public abstract List<ScenarioDto> listScenarioToListScenarioDt(List<Scenario> scenarios);

    public Scenario scenarioDtoToScenario(ScenarioDto scenarioDto) {
        if (scenarioDto == null) {
            return null;
        }
        Scenario scenario = new Scenario();
        scenario.setName(scenarioDto.getName());

        return scenario;
    }


}
