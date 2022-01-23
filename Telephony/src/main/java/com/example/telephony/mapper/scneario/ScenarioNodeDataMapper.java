package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioNodeData;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import com.example.telephony.common.ScenarioQuestionParser;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public class ScenarioNodeDataMapper {
    private final ScenarioQuestionParser questionParser;

    public ScenarioNodeDataMapper() {
        questionParser = new ScenarioQuestionParser();
    }

    public ScenarioNodeData fromScenarioNodeDataDto(ScenarioNodeDataDto dto) {
        if (dto == null) {
            ScenarioNodeData data = new ScenarioNodeData();
            data.setNeedAnswer(false);
            return data;
        }

        ScenarioNodeData result = new ScenarioNodeData();
        result.setNeedAnswer(dto.isNeedAnswer());
        result.setQuestion(questionParser.parseTextToScenarioQuestion(dto.getReplica()));
        result.setWaitingTime(Optional.ofNullable(dto.getWaitingTime()).orElse(0));
        return result;
    }

    public ScenarioNodeDataDto fromScenarioNodeData(ScenarioNodeData data) {
        if ( data == null ) {
            return null;
        }

        ScenarioNodeDataDto scenarioNodeDataDto = new ScenarioNodeDataDto();

        scenarioNodeDataDto.setWaitingTime( data.getWaitingTime() );
        scenarioNodeDataDto.setNeedAnswer( data.isNeedAnswer() );
        scenarioNodeDataDto.setCreated( data.getCreated() );
        scenarioNodeDataDto.setReplica(questionParser.getFullText(data.getQuestion()));
        return scenarioNodeDataDto;
    }
}
