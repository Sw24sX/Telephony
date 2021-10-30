package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.PlaybackStarted;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PlaybackStartedListener implements ApplicationListener<AsteriskEvent> {
    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {
        Event event = asteriskEvent.getEvent();
        if(event instanceof PlaybackStarted) {
            PlaybackStarted playbackStarted = (PlaybackStarted) event;
            System.out.println("PLayback started in listener");
        }
    }
}
