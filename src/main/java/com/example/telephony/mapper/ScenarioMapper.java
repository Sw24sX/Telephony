package com.example.telephony.mapper;

import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.dto.ScenarioDto;
import com.example.telephony.dto.ScenarioEdgeDto;
import com.example.telephony.dto.ScenarioNodeDto;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.ScenarioMappingException;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class ScenarioMapper {
    @Autowired
    protected ScenarioNodeMapper scenarioNodeMapper;

    public Scenario fromScenarioDto(ScenarioDto dto) {
        if (dto == null) {
            return null;
        }

        Scenario scenario = new Scenario();

        Map<Long, ScenarioNode> scenarioNodes = getMapScenarioNode(dto.getNodes());
        ScenarioNode root = getRootNode(scenarioNodes, dto.getRootId());
        connectNodes(scenarioNodes, dto.getEdges());
        if (root.getChildren() == null || root.getChildren().isEmpty()) {
            throw new ScenarioMappingException(ScenarioExceptionMessages.ROOT_NODE_HAVE_NOT_CHILD.getMessage());
        }

        scenario.setName(dto.getName());
        scenario.setRoot(root);

        return scenario;
    }

    private Map<Long, ScenarioNode> getMapScenarioNode(List<ScenarioNodeDto> scenarioNodeDtos) {
        Map<Long, ScenarioNode> result = new HashMap<>();
        for(ScenarioNodeDto node : scenarioNodeDtos) {
            result.put(node.getId(), scenarioNodeMapper.fromScenarioNodeDto(node));
        }

        return result;
    }

    private ScenarioNode getRootNode(Map<Long, ScenarioNode> nodeMap, Long rootId) {
        if(!nodeMap.containsKey(rootId)) {
            throw new ScenarioMappingException(ScenarioExceptionMessages.NO_ROOT_NODE.getMessage());
        }

        return nodeMap.get(rootId);
    }

    private void connectNodes(Map<Long, ScenarioNode> scenarioNodes, List<ScenarioEdgeDto> edges) {
        for(ScenarioEdgeDto edge : edges) {
            ScenarioNode parent = getScenarioNode(scenarioNodes, edge.getSource());
            ScenarioNode child = getScenarioNode(scenarioNodes, edge.getTarget());

            parent.getChildren().add(child);
            child.getParents().add(parent);
        }
    }

    private ScenarioNode getScenarioNode(Map<Long, ScenarioNode> scenarioNodes, Long id) {
        if(!scenarioNodes.containsKey(id)) {
            throw new ScenarioMappingException(
                    String.format(ScenarioExceptionMessages.NODE_NOT_FOUND.getMessage(), id));
        }

        return scenarioNodes.get(id);
    }
}
