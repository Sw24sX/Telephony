package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.enums.ScenarioNodeTypes;
import com.example.telephony.service.scenario.dialing.steps.EndStep;
import com.example.telephony.service.scenario.dialing.steps.SoundStep;

public class ScenarioBuilder {
    private ScenarioStep firstStep;
    private ScenarioStep lastStep;

    private ScenarioBuilder() {
    }

    private void addScenarioStep(ScenarioStep scenarioStep) {
        if(lastStep != null) {
            lastStep.setNext(scenarioStep);
            lastStep = scenarioStep;
        }

        if(firstStep == null) {
            firstStep = scenarioStep;
            lastStep = scenarioStep;
        }
    }

    public static ScenarioStep build(Scenario scenario, ARI ari) {
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder();
        //todo: may be not only one children

        ScenarioNode current = scenario.getRoot();
        while(current != null) {
            switch (current.getType()){
                case INPUT:
                    current = getNextNode(current);
                    break;
                case QUESTION:
                    scenarioBuilder.addScenarioStep(new SoundStep(current, ari));
                    current = getNextNode(current);
                    break;
                case OUTPUT:
                    scenarioBuilder.addScenarioStep(new EndStep(current, ari));
                    current = getNextNode(current);
                    break;
            }
        }

        return scenarioBuilder.firstStep;
    }

    private static ScenarioNode getNextNode(ScenarioNode current) {
        if (current.getChildren().size() == 0) {
            return null;
        }
        return current.getChildren().get(0);
    }
}
