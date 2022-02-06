package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.dto.scenario.ScenarioEdgeDto;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ScenarioEdgeMapper {
    public ScenarioEdgeDto fromScenarioEdge(ScenarioEdge scenarioEdge) {
        if (scenarioEdge == null) {
            return null;
        }

        ScenarioEdgeDto edge = new ScenarioEdgeDto();
        String sourceId = scenarioEdge.getSource().getId().toString();
        String targetId = scenarioEdge.getTarget().getId().toString();

        UUID uuid = UUID.randomUUID();
        edge.setId(String.format(uuid.toString(), sourceId, targetId));
        edge.setSource(sourceId);
        edge.setTarget(targetId);
        String sourceHandle = scenarioEdge.getAnswerKey() != null ? scenarioEdge.getId().toString() : null;
        edge.setSourceHandle(sourceHandle);
        return edge;
    }
}
