package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.Scenario;

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
//        for(Sound sound : scenario.getSounds()) {
//            scenarioBuilder.addScenarioStep(new ScenarioStepImpl(ari, sound));
//        }
        return scenarioBuilder.firstStep;
    }
}
