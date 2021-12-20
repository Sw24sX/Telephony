package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.exception.ScenarioBuildException;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.CallerBaseService;
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

    @Autowired
    public AriService(AsteriskHelper asteriskHelper, ScenarioManager scenarioManager,
                      ScenarioPreparationService scenarioPreparationService, CallerBaseService callerBaseService,
                      CallerRepository callerRepository) {
        this.asteriskHelper = asteriskHelper;
        this.scenarioManager = scenarioManager;
        this.scenarioPreparationService = scenarioPreparationService;
        this.callerBaseService = callerBaseService;
        this.callerRepository = callerRepository;
    }

    public void startDialingCallersBase(Long callersBaseId, Scenario scenario) {
        CallersBase callersBase = callerBaseService.getById(callersBaseId);
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, asteriskHelper);
        // TODO: 19.12.2021 get callers base by page
        for(Caller caller : callersBase.getCallers()) {
            try {
                addCallerToScenarioExecute(caller, scenarioStep);
            } catch (ScenarioBuildException e) {
                // TODO: 17.12.2021 add to statistic
                continue;
            } catch (RestException e) {
                // TODO: 17.12.2021 add to statistic
                continue;
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
