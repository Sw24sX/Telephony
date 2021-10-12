package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.actions.ActionEvents;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;

public class Asterisk {
    private static Asterisk ASTERISK = null;

    private final String url;
    private final String username;
    private final String password;
    private final String app;
    private ActionEvents actionEvents;
    private final ARI ari;

    private Asterisk(String url, String username, String password, String app) throws ARIException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.app = app;
        this.ari = ARI.build(url, app, username, password, AriVersion.IM_FEELING_LUCKY);
        this.actionEvents = ari.getActionImpl(ActionEvents.class);
    }

    public void setMessageCallBack(MessageCallBack messageCallBack) throws RestException {
        this.actionEvents.eventWebsocket(this.app).execute(messageCallBack);
    }

    public void call(String number) throws RestException {
        ari.channels().originate(number).setExtension(number).setApp(app).execute();
    }

    public static Asterisk create(String url, String username, String password, String app) throws ARIException {
        if (ASTERISK == null) {
            ASTERISK = new Asterisk(url, username, password, app);
        }

        return ASTERISK;
    }
}
