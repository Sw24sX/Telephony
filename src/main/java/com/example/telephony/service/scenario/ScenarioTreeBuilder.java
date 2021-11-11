package com.example.telephony.service.scenario;

import com.example.telephony.domain.ScenarioStepEntity;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.MappingException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.ScenarioStepEntityRepository;

import java.util.ArrayList;
import java.util.List;

public class ScenarioTreeBuilder {
    private final ScenarioStepEntityRepository scenarioStepEntityRepository;
    private final List<ScenarioStepEntity> parents;

    public ScenarioTreeBuilder(ScenarioStepEntityRepository scenarioStepEntityRepository) {
        this.scenarioStepEntityRepository = scenarioStepEntityRepository;
        parents = new ArrayList<>();
    }

    public ScenarioStepEntity buildAndSaveTree(List<ScenarioStepEntity> scenarioStepEntities) {
        ScenarioStepEntity root = getRootNode(scenarioStepEntities);
        saveNode(root);
        for (int rowNumber = 2; rowNumber <= getHeightTree(scenarioStepEntities); rowNumber++) {
            for(int i = 0; i < getCountNodesInRow(rowNumber); i++) {
                ScenarioStepEntity currentNode = scenarioStepEntities.get(getIndexNodeInList(rowNumber, i));
                if(currentNode == null) {
                    parents.add(null);
                    continue;
                }

                currentNode.setParent(getParent(rowNumber, i));
                parents.add(scenarioStepEntityRepository.save(currentNode));
            }
        }

        return root;
    }

    private ScenarioStepEntity getRootNode(List<ScenarioStepEntity> scenarioStepEntities) {
        if(scenarioStepEntities.isEmpty()) {
            throw new MappingException(ExceptionMessage.NOT_ANY_SCENARIO_STEP.getMessage());
        }

        ScenarioStepEntity root = scenarioStepEntities.get(0);
        if (root == null) {
            throw new MappingException(ExceptionMessage.NO_FIRST_STEP_SCENARIO.getMessage());
        }

        return root;
    }

    private void saveNode(ScenarioStepEntity scenarioStep) {
        parents.add(scenarioStepEntityRepository.save(scenarioStep));
    }

    private int getHeightTree(List<ScenarioStepEntity> scenarioStepEntities) {
        return (int) log2(scenarioStepEntities.size() + 1);
    }

    private static double log2(double logNumber) {
        return Math.log(logNumber) / Math.log(2);
    }

    private int getCountNodesInRow(int rowNumber) {
        return (int) Math.pow(2, rowNumber - 1);
    }

    private int getIndexNodeInList(int rowNumber, int numberInRow) {
        return getCountNodesInRow(rowNumber) + numberInRow - 1;
    }

    private ScenarioStepEntity getParent(int childRowNumber, int childNumberInRow) {
        int parentIndex = getIndexNodeInList(childRowNumber - 1, childNumberInRow / 2);
        ScenarioStepEntity parent = parents.get(parentIndex);
        if(parent == null) {
            throw new TelephonyException(ExceptionMessage.SCENARIO_IS_NO_TREE.getMessage());
        }
        return parent;
    }
}
