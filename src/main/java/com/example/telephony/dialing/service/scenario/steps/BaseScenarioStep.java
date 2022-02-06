package com.example.telephony.dialing.service.scenario.steps;

import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.dialing.service.asterisk.AsteriskHelper;

public abstract class BaseScenarioStep implements ScenarioStep {
    protected static final String EMPTY_ANSWER = "undefined_key";

    protected final ScenarioNode scenarioNode;
    protected final AsteriskHelper asteriskHelper;

    protected BaseScenarioStep(ScenarioNode scenarioNode, AsteriskHelper asteriskHelper) {
        this.scenarioNode = scenarioNode;
        this.asteriskHelper = asteriskHelper;
    }

    @Override
    public boolean needUserAnswer() {
        return scenarioNode.getData().isNeedAnswer();
    }

    public ScenarioNode getScenarioNode() {
        return scenarioNode;
    }

    public static String getEmptyAnswer() {
        return EMPTY_ANSWER;
    }
}
