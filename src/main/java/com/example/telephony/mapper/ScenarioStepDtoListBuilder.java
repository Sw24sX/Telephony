package com.example.telephony.mapper;

import com.example.telephony.domain.ScenarioStepEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ScenarioStepDtoListBuilder {
    private final List<ScenarioStepEntity> stepEntityList;
    private final Queue<ScenarioStepEntity> stepEntityQueue;

    public ScenarioStepDtoListBuilder() {
        stepEntityList = new ArrayList<>();
        stepEntityQueue = new LinkedList<>();
    }

    public List<ScenarioStepEntity> buildListScenario(ScenarioStepEntity rootNode) {
        ScenarioStepEntity currentNode = rootNode;
        while(currentNode != null) {
            addToStepEntityList(currentNode);
            stepEntityQueue.addAll(currentNode.getChildren());
            currentNode = stepEntityQueue.poll();
        }

        return stepEntityList;
    }

    private void addToStepEntityList(ScenarioStepEntity scenarioStep) {
        while(stepEntityList.size() <= scenarioStep.getIndexInList()) {
            stepEntityList.add(null);
        }

        stepEntityList.set(scenarioStep.getIndexInList(), scenarioStep);
    }
}
