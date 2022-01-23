package com.example.telephony.service.scenario.event.listener;

import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.PlaybackFinished;
import com.example.telephony.service.asterisk.AsteriskEvent;
import com.example.telephony.service.scenario.manager.ScenarioManager;
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
        String channelId = scenarioManager.getChannelId(playbackFinished.getPlayback().getId());
        scenarioManager.endPlayback(playbackFinished.getPlayback().getId());
        scenarioManager.continueScenarioIfPossible(channelId);
    }
}
