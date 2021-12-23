package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.Dialing;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.enums.DialingResultHoldOnMessages;
import com.example.telephony.exception.ScenarioBuildException;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.CallerBaseService;
import com.example.telephony.service.DialingCallerResultService;
import com.example.telephony.service.ScenarioPreparationService;
import com.example.telephony.service.scenario.ScenarioBuilder;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AriService {
    private final AsteriskHelper asteriskHelper;
    private final ScenarioManager scenarioManager;
    private final ScenarioPreparationService scenarioPreparationService;
    private final CallerBaseService callerBaseService;
    private final CallerRepository callerRepository;
    private final DialingCallerResultService dialingCallerResultService;

    @Autowired
    public AriService(AsteriskHelper asteriskHelper, ScenarioManager scenarioManager,
                      ScenarioPreparationService scenarioPreparationService, CallerBaseService callerBaseService,
                      CallerRepository callerRepository, DialingCallerResultService dialingCallerResultService) {
        this.asteriskHelper = asteriskHelper;
        this.scenarioManager = scenarioManager;
        this.scenarioPreparationService = scenarioPreparationService;
        this.callerBaseService = callerBaseService;
        this.callerRepository = callerRepository;
        this.dialingCallerResultService = dialingCallerResultService;
    }

    public void startDialingCallersBase(Dialing dialing) {
        CallersBase callersBase = callerBaseService.getById(dialing.getCallersBaseId());
        ScenarioStep scenarioStep = ScenarioBuilder.build(dialing.getScenario(), asteriskHelper);
        // TODO: 19.12.2021 get callers base by page
        for(Caller caller : callersBase.getCallers()) {
            try {
                addCallerToScenarioExecute(caller, scenarioStep);
            } catch (ScenarioBuildException e) {
                dialingCallerResultService.createHoldOn(caller, dialing, DialingResultHoldOnMessages.INCORRECT_CALLER_VARIABLE);
            } catch (RestException e) {
                dialingCallerResultService.createHoldOn(caller, dialing, DialingResultHoldOnMessages.INCORRECT_NUMBER);
            }
        }
    }

    private void addCallerToScenarioExecute(Caller caller, ScenarioStep scenarioStep) throws ScenarioBuildException, RestException {
        String channelId = UUID.randomUUID().toString();
        Map<ScenarioStep, GeneratedSound> sounds = scenarioPreparationService.voiceOverScenarioByCaller(scenarioStep, caller);
        scenarioManager.addCallScenario(channelId, scenarioStep, sounds);
        // TODO: 20.12.2021 optimise get phone number
        asteriskHelper.createChannel( callerRepository.getCallerNumber(caller.getId()), channelId);
    }
}
