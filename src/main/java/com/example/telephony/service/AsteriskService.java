package com.example.telephony.service;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.actions.ActionEvents;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.CallStatistic;
import com.example.telephony.domain.Caller;
import com.example.telephony.service.asterisk.MessageCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AsteriskService {
    private final String app;
    private final ARI ari;
    private ActionEvents actionEvents;
    private final CallStatisticService callStatisticService;
    private final CallerService callerService;

    @Autowired
    public AsteriskService(CallStatisticService callStatisticService, CallerService callerService,
                           Environment environment, MessageCallBack messageCallBack) throws ARIException {
        this.callStatisticService = callStatisticService;
        String url = environment.getProperty("asterisk.url");
        String username = environment.getProperty("asterisk.username");
        String password = environment.getProperty("asterisk.password");
        this.app = environment.getProperty("asterisk.app");
        this.ari = ARI.build(url, app, username, password, AriVersion.IM_FEELING_LUCKY);
        this.actionEvents = ari.getActionImpl(ActionEvents.class);
        this.actionEvents.eventWebsocket(this.app).execute(messageCallBack);
        this.callerService = callerService;
    }

    public void callByCaller(Caller caller) throws RestException {
        String number = caller.getNumber();
        CallStatistic callStatistic = new CallStatistic();
        callStatistic.setCaller(caller);
        callStatistic = callStatisticService.create(callStatistic);
        Channel channel = ari.channels().originate(number).setExtension(number).setApp(app).execute();
        callStatistic.setChannel(channel.getId());
        callStatisticService.update(callStatistic);
    }

    public void callAll() throws RestException {
        for (Caller caller : callerService.getAll()) {
            callByCaller(caller);
        }
    }
}
