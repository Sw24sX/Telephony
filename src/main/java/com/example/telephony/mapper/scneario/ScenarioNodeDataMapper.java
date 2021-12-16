package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioNodeData;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ScenarioNodeDataMapper {
    public ScenarioNodeData fromScenarioNodeDataDto(ScenarioNodeDataDto dto) {
        if (dto == null) {
            ScenarioNodeData data = new ScenarioNodeData();
            data.setNeedAnswer(false);
            return data;
        }

        ScenarioNodeData result = new ScenarioNodeData();
        result.setNeedAnswer(dto.isNeedAnswer());
        // TODO: 16.12.2021
//        result.setQuestion(dto.getReplica());
        result.setWaitingTime(Optional.ofNullable(dto.getWaitingTime()).orElse(0));
        return result;
    }

    // TODO: 16.12.2021
    @Mappings({
            @Mapping(source = "question", target = "replica", ignore = true)
    })
    public abstract ScenarioNodeDataDto fromScenarioNodeData(ScenarioNodeData data);
}
