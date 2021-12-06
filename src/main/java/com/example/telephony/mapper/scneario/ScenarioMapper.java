package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.ScenarioEdge;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.dto.scenario.ScenarioDto;
import com.example.telephony.dto.scenario.ScenarioEdgeDto;
import com.example.telephony.dto.scenario.ScenarioNodeDto;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.ScenarioMappingException;
import com.example.telephony.exception.TelephonyException;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class ScenarioMapper {
    @Autowired
    protected ScenarioNodeMapper scenarioNodeMapper;

    public ScenarioDto fromScenario(Scenario scenario) {
        if (scenario == null) {
            return null;
        }

        ScenarioDto dto = new ScenarioDto();
        dto.setId(scenario.getId());
        dto.setCreated(scenario.getCreated());
        dto.setName(scenario.getName());
        dto.setRootId(scenario.getRoot().getId().toString());
        return addNodesAndEdges(scenario.getRoot(), dto);
    }

    public Scenario fromScenarioDto(ScenarioDto dto) {
        if (dto == null) {
            return null;
        }

        Scenario scenario = new Scenario();

        Map<String, ScenarioNode> scenarioNodes = getMapScenarioNode(dto.getNodes());
        ScenarioNode root = getRootNode(scenarioNodes, dto.getRootId());
        connectNodes(scenarioNodes, dto.getEdges());
        if (CollectionUtils.isEmpty(root.getChildEdges())) {
            throw new ScenarioMappingException(ScenarioExceptionMessages.ROOT_NODE_HAVE_NOT_CHILD.getMessage());
        }

        scenario.setName(dto.getName());
        scenario.setRoot(root);

        return scenario;
    }

    private ScenarioDto addNodesAndEdges(ScenarioNode root, ScenarioDto scenarioDto) {
        List<ScenarioNodeDto> nodes = new ArrayList<>();
        List<ScenarioEdgeDto> edges = new ArrayList<>();

        Queue<ScenarioNode> children = new LinkedList<>();
        children.add(root);
        while(!children.isEmpty()) {
            ScenarioNode currentNode = children.poll();
//            for (ScenarioNode child : currentNode.getChildren()) {
//                children.add(child);
//
//                ScenarioEdgeDto edge = new ScenarioEdgeDto();
//                edge.setId(String.format("e%s-%s", currentNode.getId(), child.getId()));
//                edge.setSource(currentNode.getId());
//                edge.setTarget(child.getId());
//                edges.add(edge);
//            }

            nodes.add(scenarioNodeMapper.fromScenarioNode(currentNode));
        }

        scenarioDto.setNodes(nodes);
        scenarioDto.setEdges(edges);
        return scenarioDto;
    }



    private Map<String, ScenarioNode> getMapScenarioNode(List<ScenarioNodeDto> scenarioNodeDtos) {
        Map<String, ScenarioNode> result = new HashMap<>();
        for(ScenarioNodeDto node : scenarioNodeDtos) {
            result.put(node.getId(), scenarioNodeMapper.fromScenarioNodeDto(node));
        }

        return result;
    }

    private ScenarioNode getRootNode(Map<String, ScenarioNode> nodeMap, String rootId) {
        if(!nodeMap.containsKey(rootId)) {
            throw new ScenarioMappingException(ScenarioExceptionMessages.NO_ROOT_NODE.getMessage());
        }

        return nodeMap.get(rootId);
    }

    private void connectNodes(Map<String, ScenarioNode> scenarioNodes, List<ScenarioEdgeDto> edges) {
        for(ScenarioEdgeDto edge : edges) {
            ScenarioNode parent = getScenarioNode(scenarioNodes, edge.getSource());
            ScenarioNode child = getScenarioNode(scenarioNodes, edge.getTarget());
            if (parent.getData().isNeedAnswer()){
                ScenarioEdge scenarioEdge = parent.getChildEdges().stream()
                        .filter(parentEdge -> parentEdge.getAnswerKey().equals(edge.getSourceHandle()))
                        .findFirst()
                        .orElseThrow(() -> new TelephonyException(
                                String.format(
                                        ScenarioExceptionMessages.SOURCE_HANDLER_NOT_FOUND.getMessage(), "none", edge.getSourceHandle())));
                scenarioEdge.setTarget(child);
            } else {
                ScenarioEdge scenarioEdge = new ScenarioEdge();
                scenarioEdge.setSource(parent);
                scenarioEdge.setTarget(child);
                parent.getChildEdges().add(scenarioEdge);
            }

        }
    }

    private ScenarioNode getScenarioNode(Map<String, ScenarioNode> scenarioNodes, String id) {
        if(!scenarioNodes.containsKey(id)) {
            throw new ScenarioMappingException(
                    String.format(ScenarioExceptionMessages.NODE_NOT_FOUND.getMessage(), id));
        }

        return scenarioNodes.get(id);
    }
}
