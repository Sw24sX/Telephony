package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.scenario.dialing.ScenarioStep;

public class EndStep extends BaseScenarioStep {
    public EndStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
    }

    @Override
    public Playback execute(Channel channel) {
        try {
            ari.channels().hangup(channel.getId()).execute();
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
        return null;
    }

    @Override
    public void setNext(ScenarioStep next) {

    }

    @Override
    public ScenarioStep getNext() {
        return null;
    }
}
