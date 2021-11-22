package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.scenario.dialing.ScenarioStep;

public class SoundStep extends BaseScenarioStep {
    private ScenarioStep next;
    private final boolean needUserAnswer;

    public SoundStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
        needUserAnswer = true;
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

    @Override
    public boolean needUserAnswer() {
        //todo: get in scenarioNode
        return needUserAnswer;
    }
}
