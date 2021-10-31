package com.example.telephony.service.asterisk.event.listener;

import com.example.telephony.service.asterisk.AsteriskEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PlaybackFinishListener implements ApplicationListener<AsteriskEvent> {
    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {

    }
}
