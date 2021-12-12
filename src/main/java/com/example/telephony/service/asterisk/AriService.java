package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.actions.ActionEvents;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.TTSService;
import com.example.telephony.service.scenario.ScenarioBuilder;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AriService {
    private final String app;
    private final ARI ari;
    private final ScenarioManager scenarioManager;
    private final CallerRepository callerRepository;
    private final TTSService ttsService;

    @Autowired
    public AriService(Environment environment, MessageCallBack messageCallBack,
                      ScenarioManager scenarioManager, CallerRepository callerRepository,
                      TTSService ttsService) throws ARIException {
        this.scenarioManager = scenarioManager;
        this.callerRepository = callerRepository;
        this.ttsService = ttsService;
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

    public void startScenarioExecute(List<Caller> callers, Scenario scenario) {
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ari, ttsService);
        for(Caller caller : callers) {
            addCallerToScenarioExecute(caller, scenarioStep);
        }
    }

    public void startScenarioExecute(Caller caller, Scenario scenario) {
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ari, ttsService);
        addCallerToScenarioExecute(caller, scenarioStep);
    }

    private void addCallerToScenarioExecute(Caller caller, ScenarioStep scenarioStep) {
        String channelId = UUID.randomUUID().toString();
        scenarioManager.addCallScenario(channelId, scenarioStep);
        createChannel(callerRepository.getCallerNumber(caller.getId()), channelId);
    }

    public Channel createChannel(String number, String channelId) {
        try {
            return ari.channels().originate(number).setChannelId(channelId).setExtension(number).setApp(app).execute();
        } catch (RestException e) {
//            throw new TelephonyException(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }
}
