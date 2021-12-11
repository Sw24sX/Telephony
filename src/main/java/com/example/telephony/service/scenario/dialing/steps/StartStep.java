package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;

public class StartStep extends BaseScenarioStep {
    private ScenarioStep next;

    public StartStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
    }

    @Override
    public Playback execute(String channelId) {
        return null;
    }

    @Override
    public void setNext(ScenarioStep next, String answer) {
        if (next != null) {
            throw new TelephonyException(ScenarioExceptionMessages.AFTER_START_STEP_MAY_BE_ONLY_ONE_STEP.getMessage());
        }

        this.next = next;
    }

    @Override
    public ScenarioStep getNext(String answer) {
        return next;
    }
}
