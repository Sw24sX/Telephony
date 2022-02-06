package com.example.telephony.dialing.service.scenario.event.listener;

import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.StasisEnd;
import com.example.telephony.dialing.service.asterisk.AsteriskEvent;
import com.example.telephony.dialing.service.scenario.manager.ScenarioManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StasisEndListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;

    public StasisEndListener(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {
        Event event = asteriskEvent.getEvent();
        if(event instanceof StasisEnd) {
            execute((StasisEnd) event);
        }
    }

    private void execute(StasisEnd stasisEnd) {
        scenarioManager.endCall(stasisEnd.getChannel().getId());
    }
}
