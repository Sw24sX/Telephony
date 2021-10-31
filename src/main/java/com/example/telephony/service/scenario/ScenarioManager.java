package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Channel;
import com.example.telephony.domain.Scenario;
import com.example.telephony.domain.Sound;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.HashMap;
import java.util.Map;

public class ScenarioManager {
    private final Map<String, ScenarioStep> scenarios;

    public ScenarioManager() {
        this.scenarios = new HashMap<>();
    }

    public void addCallScenario(String channelId, ScenarioStep scenarioStep) {
        scenarios.put(channelId, scenarioStep);
    }

    public ScenarioStep genNextStep(String channelId) {
        checkContainsKeyChannelId(channelId);
        ScenarioStep currentStep = scenarios.get(channelId);
        ScenarioStep result = currentStep.getNext();
        scenarios.put(channelId, result);
        return result;
    }

    public ScenarioStep getFirstStep(String channelId) {
        checkContainsKeyChannelId(channelId);
        return scenarios.get(channelId);
    }

    private void checkContainsKeyChannelId(String channelId) {
        if(!scenarios.containsKey(channelId)) {
            throw new TelephonyException(String.format(
                    ExceptionMessage.NOT_INITIALIZE_FOLDER_FOR_UPLOAD.getMessage(), channelId));
        }
    }
}
