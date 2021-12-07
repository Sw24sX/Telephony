package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.scenario.ScenarioNode;

public abstract class BaseScenarioStep implements ScenarioStep {
    protected final ScenarioNode scenarioNode;
    protected final ARI ari;

    protected BaseScenarioStep(ScenarioNode scenarioNode, ARI ari) {
        this.scenarioNode = scenarioNode;
        this.ari = ari;
    }

    @Override
    public boolean needUserAnswer() {
        return scenarioNode.getData().isNeedAnswer();
    }
}
