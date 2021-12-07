package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.domain.scenario.ScenarioNodeExtraData;
import com.example.telephony.domain.scenario.ScenarioNodePoint;
import com.example.telephony.dto.scenario.ScenarioNodeAnswersDto;
import com.example.telephony.dto.scenario.ScenarioNodeDataDto;
import com.example.telephony.dto.scenario.ScenarioNodeDto;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
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
        if (dto.getData() != null && dto.getData().isNeedAnswer()) {
            scenarioNode.setChildEdges(dto.getData().getAnswers().stream().map(answer -> {
                ScenarioEdge result = new ScenarioEdge();
                result.setSource(scenarioNode);
                result.setAnswerKey(answer.getButton());
                return result;
            }).collect(Collectors.toList()));
        }

        ScenarioNodeExtraData extraData = new ScenarioNodeExtraData();
        extraData.setPosition(new ScenarioNodePoint(dto.getPosition().x, dto.getPosition().y));
        scenarioNode.setExtraData(extraData);
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
        data.setAnswers(getAnswersDto(scenarioNode));
        result.setData(data);
        ScenarioNodePoint position = scenarioNode.getExtraData().getPosition();
        result.setPosition(new Point(position.getX(), position.getY()));
        return result;
    }

    private List<ScenarioNodeAnswersDto> getAnswersDto(ScenarioNode scenarioNode) {
        List<ScenarioNodeAnswersDto> answers = scenarioNode.getChildEdges().stream()
                .filter(edge -> edge.getAnswerKey() != null)
                .map(edge -> new ScenarioNodeAnswersDto(edge.getAnswerKey()))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(answers) ? null : answers;
    }
}
