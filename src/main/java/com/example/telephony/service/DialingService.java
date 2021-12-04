package com.example.telephony.service;

import ch.loway.oss.ari4java.generated.models.Channel;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.Scenario;
import com.example.telephony.service.asterisk.ARIService;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class DialingService {
    private final ARIService ariService;
    private final ScenarioService scenarioService;
    private final CallerService callerService;
    private final CallerBaseService callerBaseService;
    private final Logger log;

    @Autowired
    public DialingService(ARIService ariService, ScenarioService scenarioService, CallerService callerService,
                          CallerBaseService callerBaseService) {
        this.ariService = ariService;
        this.scenarioService = scenarioService;
        this.callerService = callerService;
        this.callerBaseService = callerBaseService;
        this.log = Logger.getLogger("dialing");
    }

    public void callCaller(Long callerId) {
        String number = callerService.getCallerNumber(callerId);
        UUID uuid = UUID.randomUUID();
        Channel channel = ariService.createChannel(number, uuid.toString());
    }

    public void startDialingCaller(Long callerId, Long scenarioId) {
        Scenario scenario = scenarioService.getById(scenarioId);
        Caller caller = callerService.getById(callerId);
        ariService.startScenarioExecute(caller, scenario);
    }

    public void startDialingCallersBase(Long callersBaseId, Long scenarioId) {
        System.out.println("Start work dialing");
        Scenario scenario = scenarioService.getById(scenarioId);
        System.out.println("Find scenario");
        CallersBase callersBase = callerBaseService.getById(callersBaseId);
        System.out.println("Find caller base");
        ariService.startScenarioExecute(callersBase.getCallers(), scenario);
    }

    public void addScheduledDialing() {
        //TODO
        throw new NotImplementedException();
    }
}
