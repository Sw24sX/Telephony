package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.generated.models.StasisStart;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.ARIService;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.dialing.ScenarioStep;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StasisStartListener implements ApplicationListener<AsteriskEvent> {
    private final ARI ari;
    private final ScenarioManager scenarioManager;

    public StasisStartListener(ARIService ariService, ScenarioManager scenarioManager) {
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
        ScenarioStep scenarioStep = scenarioManager.getCurrentStep(channel.getId());
        Playback playback = scenarioStep.execute(channel.getId());
        scenarioManager.addPlayback(channel.getId(), playback.getId());
    }
}
