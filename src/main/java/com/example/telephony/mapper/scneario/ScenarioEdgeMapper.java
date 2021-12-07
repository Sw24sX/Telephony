package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.dto.scenario.ScenarioEdgeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ScenarioEdgeMapper {
    public ScenarioEdgeDto fromScenarioEdge(ScenarioEdge scenarioEdge) {
        if (scenarioEdge == null) {
            return null;
        }

        ScenarioEdgeDto edge = new ScenarioEdgeDto();
        String sourceId = scenarioEdge.getSource().getId().toString();
        String targetId = scenarioEdge.getTarget().getId().toString();

        edge.setId(String.format("e%s-%s", sourceId, targetId));
        edge.setSource(sourceId);
        edge.setTarget(targetId);
        edge.setSourceHandle(scenarioEdge.getAnswerKey());
        return edge;
    }
}
