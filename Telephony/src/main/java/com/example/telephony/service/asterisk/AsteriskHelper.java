package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.actions.ActionEvents;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.exception.TelephonyException;
import org.springframework.core.env.Environment;

public class AsteriskHelper {
    private final String app;
    private final ARI ari;

    public AsteriskHelper(Environment environment, MessageCallBack messageCallBack) throws ARIException {
        String url = environment.getProperty("asterisk.url");
        String username = environment.getProperty("asterisk.username");
        String password = environment.getProperty("asterisk.password");
        this.app = environment.getProperty("asterisk.app");
        this.ari = ARI.build(url, app, username, password, AriVersion.IM_FEELING_LUCKY);
        ari.getActionImpl(ActionEvents.class).eventWebsocket(this.app).execute(messageCallBack);
    }

    public ARI getAri() {
        return ari;
    }

    public Channel createChannel(String number, String channelId) throws RestException {
        return ari.channels()
                .originate(number)
                .setChannelId(channelId)
                .setExtension(number)
                .setApp(app)
                .execute();
    }

    public Playback createPlayback(String channelId, String mediaUrl) {
        try {
            return ari.channels().play(channelId, mediaUrl).execute();
        } catch (RestException e) {
            throw new TelephonyException(e);
        }
    }

    public void closeChannel(String channelId) {
        try {
            ari.channels().hangup(channelId).execute();
        } catch (RestException e) {
            throw new TelephonyException(e);
        }
    }
}
