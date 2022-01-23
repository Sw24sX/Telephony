package com.example.telephony.service;

import com.example.telephony.common.ScenarioQuestionParser;
import com.example.telephony.domain.scenario.*;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.enums.ScenarioNodeTypes;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.ScenarioHeaderRepository;
import com.example.telephony.repository.ScenarioNodeRepository;
import com.example.telephony.repository.ScenarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final ScenarioHeaderRepository scenarioHeaderRepository;
    private final ScenarioNodeRepository scenarioNodeRepository;
    private final ScenarioQuestionParser parser;

    public ScenarioService(ScenarioRepository scenarioRepository, ScenarioHeaderRepository scenarioHeaderRepository,
                           ScenarioNodeRepository scenarioNodeRepository) {
        this.scenarioRepository = scenarioRepository;
        this.scenarioHeaderRepository = scenarioHeaderRepository;
        this.scenarioNodeRepository = scenarioNodeRepository;
        parser = new ScenarioQuestionParser();
    }

    public Page<ScenarioHeader> getAll(int number, int size, FieldsPageSort fieldsPageSort,
                                       Sort.Direction direction, String name) {
        Sort sort = Sort.by(direction, fieldsPageSort.getFieldName());
        Pageable pageable = PageRequest.of(number, size, sort);
        return scenarioHeaderRepository.findAll("%" + name + "%", pageable);
    }

    public Scenario getById(Long id) {
        Scenario scenario = scenarioRepository.findById(id).orElse(null);
        if(scenario == null) {
            throw new EntityNotFoundException(String.format(ExceptionMessage.SCENARIO_NOT_FOUND.getMessage(), id));
        }

        return scenario;
    }

    public Scenario create(String name) {
        Scenario patternScenario = buildStartPatternScenario();
        patternScenario.setName(name);
        return scenarioRepository.save(patternScenario);
    }

    private Scenario buildStartPatternScenario() {
        Scenario scenario = new Scenario();

        ScenarioNodePoint startPoint = new ScenarioNodePoint(500, 120);
        ScenarioNodePoint replicaPoint = new ScenarioNodePoint(500, 400);
        ScenarioNodePoint finishPoint = new ScenarioNodePoint(500, 800);

        ScenarioNode start = createPatternNode(ScenarioNodeTypes.START, createNotReplicaData(), startPoint);
        ScenarioNode finish = createPatternNode(ScenarioNodeTypes.FINISH, createNotReplicaData(), finishPoint);
        ScenarioNode replica = createPatternNode(ScenarioNodeTypes.REPLICA, createReplicaData(), replicaPoint);

        start.getChildEdges().add(createEdge(start, replica));
        replica.getChildEdges().add(createEdge(replica, finish));

        scenario.setRoot(start);
        scenario.setCountSteps(3);
        return scenario;
    }

    private ScenarioNodeData createNotReplicaData() {
        ScenarioNodeData data = new ScenarioNodeData();
        data.setWaitingTime(0);
        data.setNeedAnswer(false);
        return data;
    }

    private ScenarioNodeData createReplicaData() {
        ScenarioNodeData data = new ScenarioNodeData();
        data.setQuestion(parser.parseTextToScenarioQuestion("Текст реплики"));
        int defaultWaitingTime = 5000;
        data.setWaitingTime(defaultWaitingTime);
        data.setNeedAnswer(false);
        return data;
    }

    private ScenarioNode createPatternNode(ScenarioNodeTypes type, ScenarioNodeData data, ScenarioNodePoint position) {
        ScenarioNode result = new ScenarioNode();
        result.setData(data);
        result.setType(type);
        result.setChildEdges(new ArrayList<>());

        ScenarioNodeExtraData extraData = new ScenarioNodeExtraData();
        extraData.setPosition(position);
        result.setExtraData(extraData);
        return result;
    }

    private ScenarioEdge createEdge(ScenarioNode parent, ScenarioNode child) {
        ScenarioEdge edge = new ScenarioEdge();
        edge.setSource(parent);
        edge.setTarget(child);
        return edge;
    }

    public Scenario update(Scenario scenario, Long id) {
        Scenario oldScenario = getById(id);
        Long oldRootNodeId = oldScenario.getRoot().getId();
        BeanUtils.copyProperties(scenario, oldScenario, "id", "created");
        Scenario newScenario = scenarioRepository.save(oldScenario);
        scenarioNodeRepository.deleteById(oldRootNodeId);
        return newScenario;
    }

    public void delete(Long id) {
        Scenario scenario = getById(id);
        scenarioRepository.delete(scenario);
    }
}
