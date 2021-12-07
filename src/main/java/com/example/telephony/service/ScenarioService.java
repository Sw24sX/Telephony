package com.example.telephony.service;

import com.example.telephony.domain.scenario.*;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.enums.ScenarioNodeTypes;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.ScenarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// TODO: пустые скобки в аннотации
@Service()
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final TTSService ttsService;

    public ScenarioService(ScenarioRepository scenarioRepository, TTSService ttsService) {
        this.scenarioRepository = scenarioRepository;
        this.ttsService = ttsService;
    }

    public List<Scenario> getAll() {
        return scenarioRepository.findAll();
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
        ScenarioNode finish = createPatternNode(ScenarioNodeTypes.FINISH, createNotReplicaData(), replicaPoint);
        ScenarioNode replica = createPatternNode(ScenarioNodeTypes.REPLICA, createReplicaData(), finishPoint);

        start.getChildEdges().add(createEdge(start, replica));
        replica.getChildEdges().add(createEdge(replica, finish));

        scenario.setRoot(start);
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
        data.setQuestion("Текст реплики");
        data.setWaitingTime(50000);
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
        delete(id);
        return scenarioRepository.save(scenario);
    }

    public void delete(Long id) {
        Scenario scenario = getById(id);
        scenarioRepository.delete(scenario);
    }
}
