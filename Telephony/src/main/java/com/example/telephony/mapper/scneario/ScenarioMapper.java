package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.dto.scenario.ScenarioDto;
import com.example.telephony.dto.scenario.ScenarioEdgeDto;
import com.example.telephony.dto.scenario.ScenarioNodeDto;
import com.example.telephony.enums.exception.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.ScenarioMappingException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class ScenarioMapper {
    @Autowired
    protected ScenarioNodeMapper scenarioNodeMapper;

    @Autowired
    protected ScenarioEdgeMapper scenarioEdgeMapper;

    public ScenarioDto fromScenario(Scenario scenario) {
        ScenarioDto dto = createScenarioDtoFromScenario(scenario);
        if (dto == null) {
            return null;
        }
        return addNodesAndEdges(scenario.getRoot(), dto);
    }

    @Mappings({
            @Mapping(source = "root.id", target = "rootId"),
            @Mapping(source = "lastConnectedCallersBaseId", target = "connectedCallerBaseId")
    })
    protected abstract ScenarioDto createScenarioDtoFromScenario(Scenario scenario);

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

    public Scenario fromScenarioDto(ScenarioDto dto) {
        if (dto == null) {
            return null;
        }



        Map<String, ScenarioNode> scenarioNodes = new HashMap<>();
        Map<ScenarioNode, Map<String, ScenarioEdge>> scenarioEdgesWithAnswers = new HashMap<>();
        for(ScenarioNodeDto nodeDto : dto.getNodes()) {
            ScenarioNode node = scenarioNodeMapper.fromScenarioNodeDto(nodeDto);
            scenarioNodes.put(nodeDto.getId(), node);
            scenarioEdgesWithAnswers.put(node, scenarioNodeMapper.setEdgesToScenarioNode(node, nodeDto));
        }

        ScenarioNode root = getScenarioNode(scenarioNodes, dto.getRootId());
        connectNodes(scenarioNodes, dto.getEdges(), scenarioEdgesWithAnswers);
        if (CollectionUtils.isEmpty(root.getChildEdges())) {
            throw new ScenarioMappingException(ScenarioExceptionMessages.ROOT_NODE_HAVE_NOT_CHILD.getMessage());
        }

        Scenario scenario = new Scenario();
        scenario.setName(dto.getName());
        scenario.setRoot(root);
        scenario.setCountSteps(scenarioNodes.size());
        scenario.setLastConnectedCallersBaseId(dto.getConnectedCallerBaseId());

        return scenario;
    }

    private void connectNodes(Map<String, ScenarioNode> scenarioNodes, List<ScenarioEdgeDto> edges,
                              Map<ScenarioNode, Map<String, ScenarioEdge>> scenarioEdgesWithAnswers) {
        for(ScenarioEdgeDto edgeDto : edges) {
            ScenarioNode parent = getScenarioNode(scenarioNodes, edgeDto.getSource());
            ScenarioNode child = getScenarioNode(scenarioNodes, edgeDto.getTarget());
            if (parent.getData() != null && parent.getData().isNeedAnswer()) {
                ScenarioEdge edge = scenarioEdgesWithAnswers.get(parent).get(edgeDto.getSourceHandle());
//                getScenarioEdgeBySourceHandle(parent, edgeDto).setTarget(child);
                edge.setTarget(child);
            } else {
                parent.getChildEdges().add(createScenarioNodeWithoutAnswerKey(parent, child));
            }
        }
    }

//    private ScenarioEdge getScenarioEdgeBySourceHandle(ScenarioNode parent, ScenarioEdgeDto edge) {
//        return parent.getChildEdges().stream()
//            .filter(parentEdge -> parentEdge.getAnswerKey().equals(edge.getSourceHandle()))
//            .findFirst()
//            .orElseThrow(() -> new TelephonyException(
//                String.format(
//                    ScenarioExceptionMessages.SOURCE_HANDLER_NOT_FOUND.getMessage(), edge.getId(), edge.getSourceHandle())));
//    }

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
