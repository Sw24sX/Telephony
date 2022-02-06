package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.ChannelDtmfReceived;
import ch.loway.oss.ari4java.generated.models.Event;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.manager.ScenarioManager;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelDtmfReceivedListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;

    public ChannelDtmfReceivedListener(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
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
            startNextScenarioStep(channelDtmfReceived);
        }
    }

    private void startNextScenarioStep(ChannelDtmfReceived channelDtmfReceived) {
        Channel channel = channelDtmfReceived.getChannel();
        scenarioManager.continueScenarioWithAnswer(channel.getId(), channelDtmfReceived.getDigit());
//        Playback playback = nextStep.execute(channel.getId());
//        scenarioManager.addPlayback(channel.getId(), playback.getId());
    }
}
