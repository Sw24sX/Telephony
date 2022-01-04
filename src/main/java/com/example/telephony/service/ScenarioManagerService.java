package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.enums.DialingResultHoldOnMessages;
import com.example.telephony.exception.ScenarioBuildException;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.asterisk.AsteriskHelper;
import com.example.telephony.service.scenario.ScenarioBuilder;
import com.example.telephony.service.scenario.dialing.DialingManager;
import com.example.telephony.service.scenario.manager.ScenarioManager;
import com.example.telephony.service.scenario.manager.StateScenarioStep;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class ScenarioManagerService {
    private final AsteriskHelper asteriskHelper;
    private final ScenarioManager scenarioManager;
    private final ScenarioPreparationService scenarioPreparationService;
    private final CallerBaseService callerBaseService;
    private final CallerRepository callerRepository;
    private final DialingCallerResultService dialingCallerResultService;
    private final DialingManager dialingManager;

    @Autowired
    public ScenarioManagerService(AsteriskHelper asteriskHelper, ScenarioManager scenarioManager,
                                  ScenarioPreparationService scenarioPreparationService, CallerBaseService callerBaseService,
                                  CallerRepository callerRepository, DialingCallerResultService dialingCallerResultService,
                                  DialingManager dialingManager) {
        this.asteriskHelper = asteriskHelper;
        this.scenarioManager = scenarioManager;
        this.scenarioPreparationService = scenarioPreparationService;
        this.callerBaseService = callerBaseService;
        this.callerRepository = callerRepository;
        this.dialingCallerResultService = dialingCallerResultService;
        this.dialingManager = dialingManager;
    }

    public void startDialingCallersBase(Dialing dialing) {
        CallersBase callersBase = callerBaseService.getById(dialing.getCallersBaseId());
        ScenarioStep scenarioStep = ScenarioBuilder.build(dialing.getScenario(), asteriskHelper);
        // TODO: 19.12.2021 get callers base by page
        for(Caller caller : callersBase.getCallers()) {
            try {
                addCallerToScenarioExecute(caller, scenarioStep, dialing);
            } catch (ScenarioBuildException e) {
                dialingCallerResultService.createHoldOn(caller, dialing, DialingResultHoldOnMessages.INCORRECT_CALLER_VARIABLE, DialCallerStatus.SCENARIO_NOT_END);
                dialingManager.endDialCaller(dialing);
            } catch (RestException e) {
                dialingCallerResultService.createHoldOn(caller, dialing, DialingResultHoldOnMessages.INCORRECT_NUMBER, DialCallerStatus.HAVEN_NOT_REACHED);
                dialingManager.endDialCaller(dialing);
            }
        }
    }

    private void addCallerToScenarioExecute(Caller caller, ScenarioStep scenarioStep, Dialing dialing) throws ScenarioBuildException, RestException {
        String channelId = UUID.randomUUID().toString();
        Map<ScenarioStep, GeneratedSound> sounds = scenarioPreparationService.voiceOverScenarioByCaller(scenarioStep, caller);
        StateScenarioStep stateScenarioStep = StateScenarioStep.getBuilder()
                .dialing(dialing)
                .scenarioStep(scenarioStep)
                .caller(caller)
                .answers(new ArrayList<>())
                .isFinished(false)
                .isStart(true)
                .sounds(sounds)
                .build();

        scenarioManager.addCallScenario(channelId, stateScenarioStep);
        // TODO: 20.12.2021 optimise get phone number
        asteriskHelper.createChannel( callerRepository.getCallerNumber(caller.getId()), channelId);
    }
}
