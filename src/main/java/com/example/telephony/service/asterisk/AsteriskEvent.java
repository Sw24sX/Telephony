package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.generated.models.Event;
import org.springframework.context.ApplicationEvent;


public class AsteriskEvent extends ApplicationEvent {
    private Event event;

    public AsteriskEvent(Object source, Event event) {
        super(source);
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
