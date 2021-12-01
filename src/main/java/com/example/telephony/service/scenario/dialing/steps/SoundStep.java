package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.exception.TelephonyException;

public class SoundStep extends BaseScenarioStep {
    private ScenarioStep next;

    public SoundStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
    }

    @Override
    public Playback execute(String channelId) {
        String mediaUrl = String.format("sound:%s", scenarioNode.getData().getSoundPath());
        try {
            return ari.channels().play(channelId, mediaUrl).execute();
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    @Override
    public void setNext(ScenarioStep next) {
        this.next = next;
    }

    @Override
    public ScenarioStep getNext() {
        return next;
    }
}
