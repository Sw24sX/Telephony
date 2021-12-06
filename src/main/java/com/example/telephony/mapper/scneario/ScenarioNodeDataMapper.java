package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.ScenarioNodeData;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class ScenarioNodeDataMapper {
    public ScenarioNodeData fromScenarioNodeDataDto(ScenarioNodeDataDto dto) {
        if (dto == null) {
            return null;
        }

        ScenarioNodeData result = new ScenarioNodeData();
        result.setNeedAnswer(dto.isNeedAnswer());
        result.setQuestion(dto.getReplica());
        return result;
    }

//    @Mapping(target = "answerKey", source = "answerKey")
    public abstract ScenarioNodeDataDto fromScenarioNodeData(ScenarioNodeData data);
}
