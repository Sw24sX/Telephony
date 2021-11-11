package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Sound;
import com.example.telephony.exception.TelephonyException;

public class ScenarioStepImpl implements ScenarioStep{
    private final ARI ari;
    private final Sound sound;
    private ScenarioStep next;

    public ScenarioStepImpl(ARI ari, Sound sound) {
        this.ari = ari;
        this.sound = sound;
    }

    @Override
    public Playback execute(Channel channel) {
        String mediaUrl = String.format("sound:%s", sound.getUri());
        try {
            return ari.channels().play(channel.getId(), mediaUrl).execute();
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
