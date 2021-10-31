package com.example.telephony.service;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.CallStatistic;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Scenario;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.ARIService;
import com.example.telephony.service.scenario.ScenarioBuilder;
import com.example.telephony.service.scenario.ScenarioManager;
import com.example.telephony.service.scenario.ScenarioStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsteriskService {
    private final String app;
    private final ARI ari;
    private final CallStatisticService callStatisticService;
    private final ScenarioManager scenarioManager;

    @Autowired
    public AsteriskService(CallStatisticService callStatisticService, ARIService ariService,
                           ScenarioManager scenarioManager) {
        this.callStatisticService = callStatisticService;
        this.ari = ariService.getAri();
        this.app = ariService.getApp();
        this.scenarioManager = scenarioManager;
    }

    public void callByCaller(Caller caller) {
        String number = caller.getNumber();
        CallStatistic callStatistic = new CallStatistic();
        callStatistic.setCaller(caller);
        callStatistic = callStatisticService.create(callStatistic);
        Channel channel = createChannel(number);
        callStatistic.setChannel(channel.getId());
        callStatisticService.update(callStatistic);
    }

    private Channel createChannel(String number) {
        try {
            return ari.channels().originate(number).setExtension(number).setApp(app).execute();
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    public void callByCallerWithScenario(Caller caller, Scenario scenario) {
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ari);
        Channel channel = createChannel(caller.getNumber());
        scenarioManager.addCallScenario(channel, scenarioStep);
    }
}
