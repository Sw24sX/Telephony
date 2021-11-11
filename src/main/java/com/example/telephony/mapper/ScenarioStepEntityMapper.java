package com.example.telephony.mapper;

import com.example.telephony.domain.ScenarioStepEntity;
import com.example.telephony.dto.ScenarioStepDto;
import com.example.telephony.enums.ExceptionMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.mapping.MappingException;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ScenarioStepEntityMapper {
    public ScenarioStepEntity scenarioStepDtoToScenarioStepEntity(ScenarioStepDto scenarioStepDto) {
        if(scenarioStepDto == null) {
            return null;
        }

        if(scenarioStepDto.getIsPositive() == null) {
            throw new MappingException(ExceptionMessage.NOT_CONCRETE_WAY.getMessage());
        }

        ScenarioStepEntity result = new ScenarioStepEntity();
        result.setQuestion(scenarioStepDto.getQuestion());
        result.setSoundPath(scenarioStepDto.getSoundPath());
        result.setPositive(scenarioStepDto.getIsPositive());
        return result;
    }

    public abstract List<ScenarioStepEntity> scenarioStepDtoToScenarioStepEntity(List<ScenarioStepDto> scenarioStepDtos);

    @Mapping(target = "isPositive", source = "positive")
    public abstract ScenarioStepDto scenarioStepEntityToScenarioStepDto(ScenarioStepEntity scenarioStep);

    public List<ScenarioStepDto> scenarioStepToScenarioStepDtoList(ScenarioStepEntity scenarioStep) {
        List<ScenarioStepEntity> scenarioStepEntities = new ScenarioStepDtoListBuilder().buildListScenario(scenarioStep);
        return scenarioStepEntities.stream()
                .map(this::scenarioStepEntityToScenarioStepDto)
                .collect(Collectors.toList());
    }
}
