package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.asterisk.AriService;
import com.example.telephony.service.scenario.ScenarioBuilder;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DialingService {
    private final AriService ariService;
    private final ScenarioService scenarioService;
    private final CallerService callerService;
    private final CallerBaseService callerBaseService;
    private final ScenarioManager scenarioManager;
    private final CallerRepository callerRepository;
    private final TTSService ttsService;
    private final ScenarioPreparationService scenarioPreparationService;

    @Autowired
    public DialingService(AriService ariService, ScenarioService scenarioService, CallerService callerService,
                          CallerBaseService callerBaseService, ScenarioManager scenarioManager,
                          CallerRepository callerRepository, TTSService ttsService,
                          ScenarioPreparationService scenarioPreparationService) {
        this.ariService = ariService;
        this.scenarioService = scenarioService;
        this.callerService = callerService;
        this.callerBaseService = callerBaseService;
        this.scenarioManager = scenarioManager;
        this.callerRepository = callerRepository;
        this.ttsService = ttsService;
        this.scenarioPreparationService = scenarioPreparationService;
    }

    public void callCaller(Long callerId) {
        String number = callerService.getCallerNumber(callerId);
        UUID uuid = UUID.randomUUID();
        ariService.createChannel(number, uuid.toString());
    }

    public void startDialingCaller(Long callerId, Long scenarioId) {
        Scenario scenario = scenarioService.getById(scenarioId);
        Caller caller = callerService.getById(callerId);
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ariService.getAri(), ttsService);
        addCallerToScenarioExecute(caller, scenarioStep);
    }

    public void startDialingCallersBase(Long callersBaseId, Long scenarioId) {
        Scenario scenario = scenarioService.getById(scenarioId);
        CallersBase callersBase = callerBaseService.getById(callersBaseId);
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ariService.getAri(), ttsService);
        for(Caller caller : callersBase.getCallers()) {
            addCallerToScenarioExecute(caller, scenarioStep);
        }
    }

    private void addCallerToScenarioExecute(Caller caller, ScenarioStep scenarioStep) {
        String channelId = UUID.randomUUID().toString();
        Map<ScenarioStep, GeneratedSound> sounds = scenarioPreparationService.voiceOverScenarioByCaller(scenarioStep, caller);
        scenarioManager.addCallScenario(channelId, scenarioStep, sounds);
        ariService.createChannel(callerRepository.getCallerNumber(caller.getId()), channelId);
    }

    public void addScheduledDialing() {
        //TODO
        throw new NotImplementedException();
    }
}
