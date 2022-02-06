package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.generated.models.ChannelHangupRequest;
import ch.loway.oss.ari4java.generated.models.Event;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.manager.ScenarioManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelHangupRequestListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;

    public ChannelHangupRequestListener(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {
        Event event = asteriskEvent.getEvent();
        if(event instanceof ChannelHangupRequest) {
            execute((ChannelHangupRequest) event);
        }
    }

    private void execute(ChannelHangupRequest channelHangupRequest) {
        scenarioManager.endCall(channelHangupRequest.getChannel().getId());
    }
}
