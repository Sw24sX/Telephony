package com.example.telephony.dialing.service.scenario.event.listener;

import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.PlaybackStarted;
import com.example.telephony.dialing.service.asterisk.AsteriskEvent;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PlaybackStartedListener implements ApplicationListener<AsteriskEvent> {
    @Override
    public void onApplicationEvent(@NonNull AsteriskEvent asteriskEvent) {
        Event event = asteriskEvent.getEvent();
        if(event instanceof PlaybackStarted) {
            PlaybackStarted playbackStarted = (PlaybackStarted) event;
            System.out.println("Playback started in listener " + playbackStarted.getPlayback().getId());
        }
    }
}
