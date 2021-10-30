package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.generated.models.StasisStart;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.exception.TelephonyException;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StasisStartListener implements ApplicationListener<AsteriskEvent> {
    private final ARI ari;

    public StasisStartListener(ARIService ariService) {
        this.ari = ariService.getAri();
    }

    @Override
    public void onApplicationEvent(AsteriskEvent asteriskEvent) {
        if(asteriskEvent.getEvent() instanceof StasisStart) {
            StasisStart stasisStart = (StasisStart) asteriskEvent.getEvent();
            System.out.println("StasisStart started in listener");
            String mediaUrl = String.format("sound:%s", "http://192.168.0.103:8080/sounds/static/hello.wav");
            try {
                ari.channels().play(stasisStart.getChannel().getId(), mediaUrl).execute(new AriCallback<Playback>() {
                    @Override
                    public void onSuccess(Playback result) {
                        System.out.println(result + result.getState());
                    }

                    @Override
                    public void onFailure(RestException e) {
                        System.out.println(e.getMessage());
                    }
                });
            } catch (RestException e) {
                throw new TelephonyException(e.getMessage());
            }
        }
    }
}
