package com.example.telephony.service.scenario.dialing;

import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor()
public class StateScenarioStep {
    @NonNull
    private final ScenarioStep scenarioStep;

    @NonNull
    private boolean isStart;

    @NonNull
    private Map<ScenarioStep, GeneratedSound> sounds;

    private boolean isFinished = false;
    private String playbackId;

    public void playbackEnd() {
        isFinished = true;
        playbackId = null;
    }

    public GeneratedSound getSoundForScenarioStep() {
        return sounds.get(scenarioStep);
    }
}
