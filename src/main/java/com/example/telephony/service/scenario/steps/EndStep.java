package com.example.telephony.service.scenario.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;

import java.util.Collections;
import java.util.List;

public class EndStep extends BaseScenarioStep {
    public EndStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
    }

    @Override
    public Playback execute(String channelId, GeneratedSound sound) {
        try {
            ari.channels().hangup(channelId).execute();
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
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
