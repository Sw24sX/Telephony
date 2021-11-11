package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Playback;

public class StateScenarioStep {
    private final ScenarioStep scenarioStep;
    private final Channel channel;
    private Playback playback;
    private boolean isFinished;

    public StateScenarioStep(ScenarioStep scenarioStep, Channel channel) {
        this.scenarioStep = scenarioStep;
        this.channel = channel;
        this.isFinished = false;
    }

    public Playback getPlayback() {
        return playback;
    }

    public void setPlayback(Playback playback) {
        this.playback = playback;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Channel getChannel() {
        return channel;
    }

    public ScenarioStep getScenarioStep() {
        return scenarioStep;
    }
}
