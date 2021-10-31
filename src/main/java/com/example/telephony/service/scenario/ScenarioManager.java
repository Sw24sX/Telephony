package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.HashMap;
import java.util.Map;

public class ScenarioManager {
    private final Map<String, StateScenarioStep> scenariosByChannelId;
    private final Map<String, StateScenarioStep> scenariosByPlaybackId;

    public ScenarioManager() {
        this.scenariosByChannelId = new HashMap<>();
        this.scenariosByPlaybackId = new HashMap<>();
    }

    public void addCallScenario(Channel channel, ScenarioStep scenarioStep) {
        StateScenarioStep stateScenarioStep = new StateScenarioStep(scenarioStep, channel);
        scenariosByChannelId.put(channel.getId(), stateScenarioStep);
    }

    public void addPlayback(Channel channel, Playback playback) {
        checkContainsKeyChannelId(channel.getId());
        StateScenarioStep stateScenarioStep = scenariosByChannelId.get(channel.getId());
        stateScenarioStep.setPlayback(playback);
        scenariosByPlaybackId.put(playback.getId(), stateScenarioStep);
    }

    public Channel endPlayback(Playback playback) {
        checkContainsKeyPlaybackId(playback.getId());
        StateScenarioStep stateScenarioStep = scenariosByPlaybackId.get(playback.getId());
        stateScenarioStep.setFinished(true);
        stateScenarioStep.setPlayback(null);
        scenariosByPlaybackId.remove(playback.getId());
        return stateScenarioStep.getChannel();
    }

    public ScenarioStep getNextStep(Channel channel) {
        checkContainsKeyChannelId(channel.getId());
        StateScenarioStep currentState = scenariosByChannelId.get(channel.getId());
        ScenarioStep currentStep = currentState.getScenarioStep();
        StateScenarioStep nextState = new StateScenarioStep(currentStep.getNext(), channel);
        scenariosByChannelId.put(channel.getId(), nextState);
        return nextState.getScenarioStep();
    }

    public ScenarioStep getCurrentStep(Channel channel) {
        checkContainsKeyChannelId(channel.getId());
        return scenariosByChannelId.get(channel.getId()).getScenarioStep();
    }

    public boolean isFinished(Channel channel) {
        checkContainsKeyChannelId(channel.getId());
        return scenariosByChannelId.get(channel.getId()).isFinished();
    }

    private void checkContainsKeyChannelId(String channelId) {
        if(!scenariosByChannelId.containsKey(channelId)) {
            throw new TelephonyException(String.format(
                    ExceptionMessage.NOT_INITIALIZE_FOLDER_FOR_UPLOAD.getMessage(), channelId));
        }
    }

    private void checkContainsKeyPlaybackId(String playbackId) {
        if(!scenariosByPlaybackId.containsKey(playbackId)) {
            throw new TelephonyException(String.format(
                    ExceptionMessage.NOT_INITIALIZE_FOLDER_FOR_UPLOAD.getMessage(), playbackId));
        }
    }
}
