package com.example.telephony.mapper;

import com.example.telephony.domain.ScenarioNodeData;
import com.example.telephony.dto.ScenarioNodeDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScenarioNodeDataMapper {

    @Mapping(target = "id", ignore = true)
    ScenarioNodeData fromScenarioNodeDataDto(ScenarioNodeDataDto dto);

    ScenarioNodeDataDto fromScenarioNodeData(ScenarioNodeData data);
}
