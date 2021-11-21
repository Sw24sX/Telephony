package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.ChannelDtmfReceived;
import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.ARIService;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.dialing.ScenarioStep;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelDtmfReceivedListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;
    private final ARI ari;

    public ChannelDtmfReceivedListener(ScenarioManager scenarioManager, ARIService ariService) {
        this.scenarioManager = scenarioManager;
        this.ari = ariService.getAri();
    }

    @Override
    public void onApplicationEvent(@NonNull AsteriskEvent asteriskEvent) {
        Event event = asteriskEvent.getEvent();
        if(event instanceof ChannelDtmfReceived) {
            execute((ChannelDtmfReceived) event);
        }
    }

    private void execute(ChannelDtmfReceived channelDtmfReceived) {
        if (scenarioManager.isFinished(channelDtmfReceived.getChannel())) {
            saveNumber(channelDtmfReceived);
            startNextScenarioStep(channelDtmfReceived);
        }
    }

    private void saveNumber(ChannelDtmfReceived channelDtmfReceived) {
        //TODO: save number
    }

    private void startNextScenarioStep(ChannelDtmfReceived channelDtmfReceived) {
        Channel channel = channelDtmfReceived.getChannel();
        ScenarioStep nextStep = scenarioManager.getNextStep(channel);
        if (nextStep == null) {
            try {
                ari.channels().hangup(channel.getId()).execute();
            } catch (RestException e) {
                throw new TelephonyException(e.getMessage());
            }
            return;
        }
        Playback playback = nextStep.execute(channel);
        scenarioManager.addPlayback(channel, playback);
    }
}
