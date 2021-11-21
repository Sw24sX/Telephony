package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;
import lombok.Data;

@Data
public class StateScenarioStep {
    private final ScenarioStep scenarioStep;
    private final String channelId;
    private String playbackId;
    private boolean isFinished;

    public StateScenarioStep(ScenarioStep scenarioStep, String channelId) {
        this.scenarioStep = scenarioStep;
        this.channelId = channelId;
        this.isFinished = false;
    }
}
