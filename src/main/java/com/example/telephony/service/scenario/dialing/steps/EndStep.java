package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;

public class EndStep extends BaseScenarioStep {
    public EndStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
    }

    @Override
    public Playback execute(String channelId) {
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
}
