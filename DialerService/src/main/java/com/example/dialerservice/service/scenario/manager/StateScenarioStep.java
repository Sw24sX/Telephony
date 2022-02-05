package com.example.dialerservice.service.scenario.manager;

import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@Builder()
public class StateScenarioStep {
    private final ScenarioStep scenarioStep;
    private final boolean isStart;
    private final Map<ScenarioStep, GeneratedSound> sounds;
    private final Dialing dialing;
    private boolean isFinished;
    private String playbackId;
    private final List<String> answers;
    private final Caller caller;
    private final LocalTime startCall;

    public void playbackEnd() {
        isFinished = true;
    }

    public GeneratedSound getSoundForScenarioStep() {
        return sounds.get(scenarioStep);
    }

    public StateScenarioStep createNextState(ScenarioStep scenarioStep) {
        return new StateScenarioStepBuilder()
                .answers(this.answers)
                .dialing(this.dialing)
                .sounds(this.sounds)
                .caller(this.caller)
                .isFinished(false)
                .isStart(false)
                .scenarioStep(scenarioStep)
                .startCall(startCall)
                .build();
    }

    public static StateScenarioStepBuilder getBuilder() {
        return new StateScenarioStepBuilder();
    }
}
