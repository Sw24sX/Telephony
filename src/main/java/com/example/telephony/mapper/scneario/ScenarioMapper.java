package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.dto.scenario.ScenarioDto;
import com.example.telephony.dto.scenario.ScenarioEdgeDto;
import com.example.telephony.dto.scenario.ScenarioNodeDto;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.ScenarioMappingException;
import com.example.telephony.exception.TelephonyException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class ScenarioMapper {
    @Autowired
    protected ScenarioNodeMapper scenarioNodeMapper;

    @Autowired
    protected ScenarioEdgeMapper scenarioEdgeMapper;

    @Mappings({

    })
    protected abstract ScenarioDto createScenarioDtoFromScenario(Scenario scenario);

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
        ScenarioNode root = getScenarioNode(scenarioNodes, dto.getRootId());
        connectNodes(scenarioNodes, dto.getEdges());
        if (CollectionUtils.isEmpty(root.getChildEdges())) {
            throw new ScenarioMappingException(ScenarioExceptionMessages.ROOT_NODE_HAVE_NOT_CHILD.getMessage());
        }

        scenario.setName(dto.getName());
        scenario.setRoot(root);
        scenario.setCountSteps(scenarioNodes.size());

        return scenario;
    }

    private ScenarioDto addNodesAndEdges(ScenarioNode root, ScenarioDto scenarioDto) {
        scenarioDto.setNodes(new ArrayList<>());
        scenarioDto.setEdges(new ArrayList<>());

        Queue<ScenarioNode> children = new LinkedList<>();
        children.add(root);

        Set<Long> addedNodeId = SetUtils.hashSet(root.getId());
        while(!children.isEmpty()) {
            ScenarioNode currentNode = children.poll();
            for (ScenarioEdge scenarioEdge : currentNode.getChildEdges()) {
                scenarioDto.getEdges().add(scenarioEdgeMapper.fromScenarioEdge(scenarioEdge));
                if(!addedNodeId.contains(scenarioEdge.getTarget().getId())) {
                    children.add(scenarioEdge.getTarget());
                    addedNodeId.add(scenarioEdge.getTarget().getId());
                }
            }
            scenarioDto.getNodes().add(scenarioNodeMapper.fromScenarioNode(currentNode));
        }

        return scenarioDto;
    }

    private Map<String, ScenarioNode> getMapScenarioNode(List<ScenarioNodeDto> scenarioNodeDtos) {
        Map<String, ScenarioNode> result = new HashMap<>();
        for(ScenarioNodeDto node : scenarioNodeDtos) {
            result.put(node.getId(), scenarioNodeMapper.fromScenarioNodeDto(node));
        }

        return result;
    }

    private void connectNodes(Map<String, ScenarioNode> scenarioNodes, List<ScenarioEdgeDto> edges) {
        for(ScenarioEdgeDto edge : edges) {
            ScenarioNode parent = getScenarioNode(scenarioNodes, edge.getSource());
            ScenarioNode child = getScenarioNode(scenarioNodes, edge.getTarget());
            if (parent.getData() != null && parent.getData().isNeedAnswer()){
                getScenarioEdgeBySourceHandle(parent, edge).setTarget(child);
            } else {
                parent.getChildEdges().add(createScenarioNodeWithoutAnswerKey(parent, child));
            }
        }
    }

    private ScenarioEdge getScenarioEdgeBySourceHandle(ScenarioNode parent, ScenarioEdgeDto edge) {
        return parent.getChildEdges().stream()
            .filter(parentEdge -> parentEdge.getAnswerKey().equals(edge.getSourceHandle()))
            .findFirst()
            .orElseThrow(() -> new TelephonyException(
                String.format(
                    ScenarioExceptionMessages.SOURCE_HANDLER_NOT_FOUND.getMessage(), edge.getId(), edge.getSourceHandle())));
    }

    private ScenarioEdge createScenarioNodeWithoutAnswerKey(ScenarioNode parent, ScenarioNode child){
        ScenarioEdge scenarioEdge = new ScenarioEdge();
        scenarioEdge.setSource(parent);
        scenarioEdge.setTarget(child);
        return scenarioEdge;
    }

    private ScenarioNode getScenarioNode(Map<String, ScenarioNode> scenarioNodes, String id) {
        if(!scenarioNodes.containsKey(id)) {
            throw new ScenarioMappingException(
                    String.format(ScenarioExceptionMessages.NODE_NOT_FOUND.getMessage(), id));
        }

        return scenarioNodes.get(id);
    }
}
