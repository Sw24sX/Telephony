package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.actions.ActionEvents;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Scenario;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.scenario.dialing.ScenarioBuilder;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.dialing.ScenarioStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ARIService {
    private final String app;
    private final ARI ari;
    private final ScenarioManager scenarioManager;

    @Autowired
    public ARIService(Environment environment, MessageCallBack messageCallBack,
                      ScenarioManager scenarioManager) throws ARIException {
        this.scenarioManager = scenarioManager;
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

    public String getApp() {
        return app;
    }

    public void startScenarioExecute(List<Caller> callers, Scenario scenario) {
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ari);
        for(Caller caller : callers) {
            addCallerToScenarioExecute(caller, scenarioStep);
        }
    }

    public void startScenarioExecute(Caller caller, Scenario scenario) {
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ari);
        addCallerToScenarioExecute(caller, scenarioStep);
    }

    private void addCallerToScenarioExecute(Caller caller, ScenarioStep scenarioStep) {
//        Channel channel = createChannel(caller.getNumber());
        Channel channel = null;
        scenarioManager.addCallScenario(channel, scenarioStep);
    }

    public Channel createChannel(String number) {
        try {
            return ari.channels().originate(number).setExtension(number).setApp(app).execute();
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
    }
}
