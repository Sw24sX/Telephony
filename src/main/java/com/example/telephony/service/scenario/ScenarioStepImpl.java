package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Sound;
import com.example.telephony.exception.TelephonyException;
import org.springframework.beans.factory.annotation.Value;

public class ScenarioStepImpl implements ScenarioStep{
    @Value("${asterisk.wait-answer}")
    private int waitAnswerTime;

    private final ARI ari;
    private final Sound sound;
    private ScenarioStep next;

    public ScenarioStepImpl(ARI ari, Sound sound) {
        this.ari = ari;
        this.sound = sound;
    }

    @Override
    public void execute(Channel channel) {
        String mediaUrl = String.format("sound:%s", sound.getUri());
        try {
            ari.channels().play(channel.getId(), mediaUrl).execute(new AriCallback<Playback>() {
                @Override
                public void onSuccess(Playback result) {
                    System.out.println(result + result.getState());
                }

                @Override
                public void onFailure(RestException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    @Override
    public void setNext(ScenarioStep next) {
        this.next = next;
    }
}
