package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.scenario.dialing.steps.ScenarioStep;

import java.util.HashMap;
import java.util.Map;

public class ScenarioManager {
    private final Map<String, StateScenarioStep> scenariosByChannelId;
    private final Map<String, String> channelIdByPlaybackId;

    public ScenarioManager() {
        this.scenariosByChannelId = new HashMap<>();
        this.channelIdByPlaybackId = new HashMap<>();
    }

    public void endCall(String channelId) {
        StateScenarioStep currentState = getCurrentState(channelId);
        if (currentState.getPlaybackId() != null) {
            channelIdByPlaybackId.remove(currentState.getPlaybackId());
        }
        scenariosByChannelId.remove(channelId);
    }

    public void addCallScenario(String channelId, ScenarioStep scenarioStep) {
        StateScenarioStep stateScenarioStep = new StateScenarioStep(scenarioStep, true);
        scenariosByChannelId.put(channelId, stateScenarioStep);
    }

    public void endPlayback(String playbackId) {
        getCurrentState(getChannelId(playbackId)).playbackEnd();
        channelIdByPlaybackId.remove(playbackId);
    }

    public String getChannelId(String playbackId) {
        checkContainsKeyPlaybackId(playbackId);
        return channelIdByPlaybackId.get(playbackId);
    }

    public void startScenario(String channelId) {
        StateScenarioStep currentState = getCurrentState(channelId);
        if(!currentState.isStart()) {
            throw new TelephonyException(
                    String.format(ExceptionMessage.SCENARIO_WAS_ALREADY_STARTED.getMessage(), channelId));
        }
        continueScenario(channelId, null);
    }

    public void continueScenarioIfPossible(String channelId) {
        StateScenarioStep currentState = getCurrentState(channelId);
        boolean needUserAnswer = currentState.getScenarioStep().needUserAnswer();
        if (currentState.isFinished() && !needUserAnswer) {
            continueScenario(channelId, null);
        }
    }

    public void continueScenarioWithAnswer(String channelId, String answer) {
        //todo save answer
        StateScenarioStep currentState = getCurrentState(channelId);
        if (currentState.isFinished()) {
            continueScenario(channelId, answer);
        }
    }

    public boolean isFinished(String channelId) {
        checkContainsKeyChannelId(channelId);
        return scenariosByChannelId.get(channelId).isFinished();
    }

    private void continueScenario(String channelId, String answer) {
        ScenarioStep nextStep = getCurrentState(channelId).getScenarioStep().getNext(answer);
        if (nextStep == null) {
            throw new TelephonyException(ExceptionMessage.SCENARIO_NO_MORE_STEPS.getMessage());
        }
        StateScenarioStep nextState = new StateScenarioStep(nextStep, false);
        scenariosByChannelId.put(channelId, nextState);
        Playback playback = nextStep.execute(channelId);
        addPlayback(channelId, playback.getId());
    }

    private void addPlayback(String channelId, String playbackId) {
        StateScenarioStep currentState = getCurrentState(channelId);
        currentState.setPlaybackId(playbackId);
        channelIdByPlaybackId.put(playbackId, channelId);
    }

    private StateScenarioStep getCurrentState(String channelId) {
        checkContainsKeyChannelId(channelId);
        return scenariosByChannelId.get(channelId);
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
