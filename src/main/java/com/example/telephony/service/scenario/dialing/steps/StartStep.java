package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.service.scenario.dialing.ScenarioStep;

public class StartStep extends BaseScenarioStep {
    private ScenarioStep next;
    private final boolean needUserAnswer;

    public StartStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
        needUserAnswer = false;
    }

    @Override
    public Playback execute(String channelId) {
        return null;
    }

    @Override
    public void setNext(ScenarioStep next) {
        this.next = next;
    }

    @Override
    public ScenarioStep getNext() {
        return next;
    }

    @Override
    public boolean needUserAnswer() {
        return needUserAnswer;
    }
}
