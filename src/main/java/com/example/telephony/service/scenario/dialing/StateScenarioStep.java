package com.example.telephony.service.scenario.dialing;

import com.example.telephony.service.scenario.dialing.steps.ScenarioStep;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor()
public class StateScenarioStep {
    @NonNull
    private final ScenarioStep scenarioStep;
    private boolean isFinished = false;
    @NonNull
    private boolean isStart;
    private String playbackId;

    public void playbackEnd() {
        isFinished = true;
        playbackId = null;
    }
}
