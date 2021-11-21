package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.HashMap;
import java.util.Map;

public class ScenarioManager {
    private final Map<String, StateScenarioStep> scenariosByChannelId;
    private final Map<String, String> channelIdByPlaybackId;

    public ScenarioManager() {
        this.scenariosByChannelId = new HashMap<>();
        this.channelIdByPlaybackId = new HashMap<>();
    }

    public void addCallScenario(String channelId, ScenarioStep scenarioStep) {
        StateScenarioStep stateScenarioStep = new StateScenarioStep(scenarioStep, channelId);
        scenariosByChannelId.put(channelId, stateScenarioStep);
    }

    public void addPlayback(String channelId, String playbackId) {
        checkContainsKeyChannelId(channelId);
        StateScenarioStep stateScenarioStep = scenariosByChannelId.get(channelId);
        stateScenarioStep.setPlaybackId(playbackId);
        channelIdByPlaybackId.put(playbackId, channelId);
    }

    public String endPlayback(String playbackId) {
        checkContainsKeyPlaybackId(playbackId);
        String channelId = channelIdByPlaybackId.get(playbackId);
        checkContainsKeyChannelId(channelId);
        StateScenarioStep stateScenarioStep = scenariosByChannelId.get(channelId);
        stateScenarioStep.setFinished(true);
        stateScenarioStep.setPlaybackId(null);
        channelIdByPlaybackId.remove(playbackId);
        return stateScenarioStep.getChannelId();
    }

    public ScenarioStep getNextStep(String channelId) {
        checkContainsKeyChannelId(channelId);
        StateScenarioStep currentState = scenariosByChannelId.get(channelId);
        if(!currentState.isFinished()) {
            throw new TelephonyException(ExceptionMessage.SCENARIO_STEP_NOT_FINISHED.getMessage());
        }

        ScenarioStep currentStep = currentState.getScenarioStep();
        StateScenarioStep nextState = new StateScenarioStep(currentStep.getNext(), channelId);
        scenariosByChannelId.put(channelId, nextState);
        return nextState.getScenarioStep();
    }

    public ScenarioStep getCurrentStep(String channelId) {
        checkContainsKeyChannelId(channelId);
        return scenariosByChannelId.get(channelId).getScenarioStep();
    }

    public boolean isFinished(String channelId) {
        checkContainsKeyChannelId(channelId);
        return scenariosByChannelId.get(channelId).isFinished();
    }

    private void checkContainsKeyChannelId(String channelId) {
        if(!scenariosByChannelId.containsKey(channelId)) {
            throw new TelephonyException(String.format(
                    ExceptionMessage.SCENARIO_MANAGER_NOT_FOUND_ID.getMessage(), channelId));
        }
    }

    private void checkContainsKeyPlaybackId(String playbackId) {
        if(!channelIdByPlaybackId.containsKey(playbackId)) {
            throw new TelephonyException(String.format(
                    ExceptionMessage.SCENARIO_MANAGER_NOT_FOUND_ID.getMessage(), playbackId));
        }
    }
}
