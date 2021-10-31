package com.example.telephony.service.asterisk.event.listener;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.PlaybackFinished;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.ScenarioManager;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PlaybackFinishListener implements ApplicationListener<AsteriskEvent> {
    private final ScenarioManager scenarioManager;

    public PlaybackFinishListener(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void onApplicationEvent(@NonNull AsteriskEvent asteriskEvent) {
        Event event = asteriskEvent.getEvent();
        if(event instanceof PlaybackFinished) {
            execute((PlaybackFinished) event);
        }
    }

    private void execute(PlaybackFinished playbackFinished) {
        Channel channel = scenarioManager.endPlayback(playbackFinished.getPlayback());
    }
}
