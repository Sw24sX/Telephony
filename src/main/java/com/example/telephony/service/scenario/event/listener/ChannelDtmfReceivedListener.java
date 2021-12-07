package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.ChannelDtmfReceived;
import ch.loway.oss.ari4java.generated.models.Event;
import com.example.telephony.service.asterisk.AriService;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelDtmfReceivedListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;
    private final ARI ari;

    public ChannelDtmfReceivedListener(ScenarioManager scenarioManager, AriService ariService) {
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
        if (scenarioManager.isFinished(channelDtmfReceived.getChannel().getId())) {
            saveNumber(channelDtmfReceived);
            startNextScenarioStep(channelDtmfReceived);
        }
    }

    private void saveNumber(ChannelDtmfReceived channelDtmfReceived) {
        //TODO: save number
    }

    private void startNextScenarioStep(ChannelDtmfReceived channelDtmfReceived) {
        Channel channel = channelDtmfReceived.getChannel();
        scenarioManager.continueScenario(channel.getId(), channelDtmfReceived.getDigit());
//        Playback playback = nextStep.execute(channel.getId());
//        scenarioManager.addPlayback(channel.getId(), playback.getId());
    }
}
