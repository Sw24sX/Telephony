package com.example.telephony.service.asterisk;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PlaybackFinishListener implements ApplicationListener<AsteriskEvent> {
    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {

    }
}
