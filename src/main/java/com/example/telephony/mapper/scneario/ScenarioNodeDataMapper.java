package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioNodeData;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import com.example.telephony.common.ScenarioQuestionParser;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ScenarioNodeDataMapper {
    protected ScenarioQuestionParser parser = new ScenarioQuestionParser();

    public ScenarioNodeData fromScenarioNodeDataDto(ScenarioNodeDataDto dto) {
        if (dto == null) {
            ScenarioNodeData data = new ScenarioNodeData();
            data.setNeedAnswer(false);
            return data;
        }

        ScenarioNodeData result = new ScenarioNodeData();
        result.setNeedAnswer(dto.isNeedAnswer());
        result.setQuestion(parser.parseTextToScenarioQuestion(dto.getReplica()));
        result.setWaitingTime(Optional.ofNullable(dto.getWaitingTime()).orElse(0));
        return result;
    }

//    // TODO: 16.12.2021
//    @Mappings({
//            @Mapping(source = "question", target = "replica", ignore = true)
//    })
    public ScenarioNodeDataDto fromScenarioNodeData(ScenarioNodeData data) {
        if ( data == null ) {
            return null;
        }

        ScenarioNodeDataDto scenarioNodeDataDto = new ScenarioNodeDataDto();

        scenarioNodeDataDto.setWaitingTime( data.getWaitingTime() );
        scenarioNodeDataDto.setNeedAnswer( data.isNeedAnswer() );
        scenarioNodeDataDto.setCreated( data.getCreated() );
        scenarioNodeDataDto.setReplica(parser.getFullText(data.getQuestion()));
        return scenarioNodeDataDto;
    }
}
