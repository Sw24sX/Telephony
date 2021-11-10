package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.TelephonyApplication;
import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.Sound;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

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
