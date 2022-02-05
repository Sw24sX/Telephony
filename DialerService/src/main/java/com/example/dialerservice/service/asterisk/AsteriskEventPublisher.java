package com.example.dialerservice.service.asterisk;

import ch.loway.oss.ari4java.generated.models.Event;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AsteriskEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public AsteriskEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishAsteriskEvent(final Event event) {
        AsteriskEvent asteriskEvent = new AsteriskEvent(this, event);
        applicationEventPublisher.publishEvent(asteriskEvent);
    }
}
