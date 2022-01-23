package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.generated.models.*;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import org.springframework.stereotype.Component;

@Component
public class MessageCallBack implements AriCallback<Message> {
    private final AsteriskEventPublisher asteriskEventPublisher;

    public MessageCallBack(AsteriskEventPublisher asteriskEventPublisher) {
        this.asteriskEventPublisher = asteriskEventPublisher;
    }

    @Override
    public void onSuccess(Message result) {
        System.out.println(result.getType());
        if(result instanceof Dial){
            Dial dial = (Dial) result;
            System.out.println(dial.getForwarded().getId());
        }
        if(result instanceof Event){
            asteriskEventPublisher.publishAsteriskEvent((Event) result);
        }
    }

    @Override
    public void onFailure(RestException e) {
        System.out.println("fail callback message");
    }
}
