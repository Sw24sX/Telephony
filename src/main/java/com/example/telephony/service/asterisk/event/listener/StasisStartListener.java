package com.example.telephony.service.asterisk.event.listener;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.generated.models.StasisStart;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.ARIService;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.ScenarioManager;
import com.example.telephony.service.scenario.ScenarioStep;
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
        System.out.println("StasisStart started in listener, channel-id: " + stasisStart.getChannel().getId());
        String channelId = stasisStart.getChannel().getId();
        ScenarioStep scenarioStep = scenarioManager.getFirstStep(channelId);
        scenarioStep.execute(stasisStart.getChannel());

//        String mediaUrl = String.format("sound:%s", "http://192.168.0.103:8080/sounds/static/hello.wav");
//        try {
//            ari.channels().play(stasisStart.getChannel().getId(), mediaUrl).execute(new AriCallback<Playback>() {
//                @Override
//                public void onSuccess(Playback result) {
//                    System.out.println(result + result.getState());
//                }
//
//                @Override
//                public void onFailure(RestException e) {
//                    System.out.println(e.getMessage());
//                }
//            });
//        } catch (RestException e) {
//            throw new TelephonyException(e.getMessage());
//        }
    }
}
