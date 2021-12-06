package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.ScenarioEdge;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.domain.ScenarioNodeData;
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

    public ScenarioNodeDto fromScenarioNode(ScenarioNode scenarioNode) {
        if(scenarioNode == null) {
            return null;
        }

        ScenarioNodeDto result = new ScenarioNodeDto();
        result.setId(scenarioNode.getId().toString());
        result.setType(scenarioNode.getType());
        ScenarioNodeDataDto data = scenarioNodeDataMapper.fromScenarioNodeData(scenarioNode.getData());
        data.setAnswers(scenarioNode.getChildEdges().stream()
                .map(edge -> new ScenarioNodeAnswersDto(edge.getAnswerKey()))
                .collect(Collectors.toList()));
        result.setData(data);
        return result;
    }
}
