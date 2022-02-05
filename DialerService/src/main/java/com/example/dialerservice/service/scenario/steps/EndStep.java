package com.example.dialerservice.service.scenario.steps;

import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.exception.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.AsteriskHelper;

import java.util.Collections;
import java.util.List;

public class EndStep extends BaseScenarioStep {
    public EndStep(ScenarioNode scenarioNode, AsteriskHelper asteriskHelper) {
        super(scenarioNode, asteriskHelper);
    }

    @Override
    public Playback execute(String channelId, GeneratedSound sound) {
        asteriskHelper.closeChannel(channelId);
        return null;
    }

    @Override
    public void setNext(ScenarioStep next, String answer) {
        throw new TelephonyException(ScenarioExceptionMessages.STEP_AFTER_END.getMessage());
    }

    @Override
    public ScenarioStep getNext(String answer) {
        throw new TelephonyException(ScenarioExceptionMessages.STEP_AFTER_END.getMessage());
    }

    @Override
    public List<ScenarioStep> getAllVariantsNext() {
        return Collections.emptyList();
    }
}
