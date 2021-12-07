package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.ChannelHangupRequest;
import ch.loway.oss.ari4java.generated.models.Event;
import com.example.telephony.service.asterisk.AriService;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelHangupRequestListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;
    private final ARI ari;

    public ChannelHangupRequestListener(ScenarioManager scenarioManager, AriService ariService) {
        this.scenarioManager = scenarioManager;
        this.ari = ariService.getAri();
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
