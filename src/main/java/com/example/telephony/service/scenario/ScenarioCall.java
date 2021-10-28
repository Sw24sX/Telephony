package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.Sound;

public class ScenarioCall {
    private ScenarioStep first;
    private ScenarioStep last;


    public void start(Channel channel) {
        first.execute(channel);
    }

    private void addScenarioStep(ScenarioStep scenarioStep) {
        if(last != null) {
            last.setNext(scenarioStep);
            last = scenarioStep;
        }

        if(first == null) {
            first = scenarioStep;
            last = scenarioStep;
        }
    }

    public static ScenarioCall build(Scenario scenario, ARI ari) {
        ScenarioCall scenarioCall = new ScenarioCall();
        for(Sound sound : scenario.getSounds()) {
            scenarioCall.addScenarioStep(new ScenarioStepImpl(ari, sound));
        }
        return scenarioCall;
    }
}
