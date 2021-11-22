package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import lombok.AllArgsConstructor;
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
}
