package com.example.telephony.service.scenario.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.AsteriskHelper;

import java.util.List;

public class StartStep extends BaseScenarioStep {
    private ScenarioStep next;

    public StartStep(ScenarioNode scenarioNode, AsteriskHelper asteriskHelper) {
        super(scenarioNode, asteriskHelper);
    }

    @Override
    public Playback execute(String channelId, GeneratedSound sound) {
        return null;
    }

    @Override
    public void setNext(ScenarioStep scenarioStep, String answer) {
        if (next != null) {
            throw new TelephonyException(ScenarioExceptionMessages.AFTER_START_STEP_MAY_BE_ONLY_ONE_STEP.getMessage());
        }

        this.next = scenarioStep;
    }

    @Override
    public ScenarioStep getNext(String answer) {
        return next;
    }

    @Override
    public List<ScenarioStep> getAllVariantsNext() {
        return List.of(next);
    }
}
