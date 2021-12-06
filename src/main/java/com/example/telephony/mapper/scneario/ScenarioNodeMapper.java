package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.ScenarioEdge;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.domain.ScenarioNodeExtraData;
import com.example.telephony.dto.scenario.ScenarioNodeAnswersDto;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import com.example.telephony.dto.scenario.ScenarioNodeDto;
import com.example.telephony.enums.ScenarioNodeTypes;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ScenarioNodeDataMapper.class})
public abstract class ScenarioNodeMapper {
    @Autowired
    protected ScenarioNodeDataMapper scenarioNodeDataMapper;

    public ScenarioNode fromScenarioNodeDto(ScenarioNodeDto dto) {
        if (dto == null) {
            return null;
        }

        ScenarioNode scenarioNode = new ScenarioNode();
        scenarioNode.setType(dto.getType());
        scenarioNode.setData(scenarioNodeDataMapper.fromScenarioNodeDataDto(dto.getData()));
        scenarioNode.setChildEdges(new ArrayList<>());
        if (dto.getData().isNeedAnswer()) {
            scenarioNode.setChildEdges(dto.getData().getAnswers().stream().map(answer -> {
                ScenarioEdge result = new ScenarioEdge();
                result.setSource(scenarioNode);
                result.setAnswerKey(answer.getButton());
                return result;
            }).collect(Collectors.toList()));
        }

        //TODO: save position
        scenarioNode.setExtraData(new ScenarioNodeExtraData());
        return scenarioNode;
    }



    @Mappings({
            @Mapping(target = "data", source = "data", resultType = ScenarioNodeDataDto.class)
    })
    public abstract ScenarioNodeDto fromScenarioNode(ScenarioNode scenarioNode);
}
