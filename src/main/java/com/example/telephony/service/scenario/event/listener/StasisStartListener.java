package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.StasisStart;
import com.example.telephony.service.asterisk.AriService;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StasisStartListener implements ApplicationListener<AsteriskEvent> {
    private final ARI ari;
    private final ScenarioManager scenarioManager;

    public StasisStartListener(AriService ariService, ScenarioManager scenarioManager) {
        this.ari = ariService.getAri();
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {
        if(asteriskEvent.getEvent() instanceof StasisStart) {
            StasisStart stasisStart = (StasisStart) asteriskEvent.getEvent();
            execute(stasisStart);
        }
    }

    private void execute(StasisStart stasisStart) {
        Channel channel = stasisStart.getChannel();
        scenarioManager.startScenario(channel.getId());
    }
}
