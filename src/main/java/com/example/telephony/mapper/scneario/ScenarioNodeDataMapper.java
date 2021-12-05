package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.ScenarioNodeData;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScenarioNodeDataMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "answerKey", source = "answerKey")
    })
    ScenarioNodeData fromScenarioNodeDataDto(ScenarioNodeDataDto dto);

    @Mapping(target = "answerKey", source = "answerKey")
    ScenarioNodeDataDto fromScenarioNodeData(ScenarioNodeData data);
}
