package com.example.telephony.service.scenario.manager;

import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.DialingCallerResultService;
import com.example.telephony.tts.service.GenerationSoundsService;
import com.example.telephony.service.scenario.dialing.DialingManager;
import com.example.telephony.service.scenario.steps.ScenarioStep;

import java.util.HashMap;
import java.util.Map;

public class ScenarioManager {
    private final GenerationSoundsService generationSoundsService;
    private final DialingCallerResultService dialingCallerResultService;
    private final DialingManager dialingManager;

    private final Map<String, StateScenarioStep> scenariosByChannelId;
    private final Map<String, String> channelIdByPlaybackId;

    public ScenarioManager(GenerationSoundsService generationSoundsService,
                           DialingCallerResultService dialingCallerResultService, DialingManager dialingManager) {
        this.generationSoundsService = generationSoundsService;
        this.dialingCallerResultService = dialingCallerResultService;
        this.dialingManager = dialingManager;
        this.scenariosByChannelId = new HashMap<>();
        this.channelIdByPlaybackId = new HashMap<>();
    }

    public void endCall(String channelId) {
        StateScenarioStep currentState = getCurrentState(channelId);
        if (currentState.getPlaybackId() != null) {
            channelIdByPlaybackId.remove(currentState.getPlaybackId());
        }
        scenariosByChannelId.remove(channelId);
        dialingCallerResultService.create(currentState.getCaller(), currentState.getDialing(), currentState.getAnswers(), currentState.getStartCall());
        dialingManager.endDialCaller(currentState.getDialing());
    }

    public void addCallScenario(String channelId, StateScenarioStep stateScenarioStep) {
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
            currentState.getAnswers().add(answer);
            continueScenario(channelId, answer);
        }
    }

    public boolean isFinished(String channelId) {
        checkContainsKeyChannelId(channelId);
        return scenariosByChannelId.get(channelId).isFinished();
    }

    private void continueScenario(String channelId, String answer) {
        StateScenarioStep stateScenarioStep = getCurrentState(channelId);
        ScenarioStep nextStep = stateScenarioStep.getScenarioStep().getNext(answer);
        if (nextStep == null) {
            throw new TelephonyException(ExceptionMessage.SCENARIO_NO_MORE_STEPS.getMessage());
        }

        StateScenarioStep nextState = stateScenarioStep.createNextState(nextStep);
        scenariosByChannelId.put(channelId, nextState);
        Playback playback = nextStep.execute(channelId, nextState.getSoundForScenarioStep());
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
