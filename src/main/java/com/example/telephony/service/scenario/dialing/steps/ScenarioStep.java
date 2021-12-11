package com.example.telephony.service.scenario.dialing.steps;

import ch.loway.oss.ari4java.generated.models.Playback;

public interface ScenarioStep {
    Playback execute(String channelId);
    void setNext(ScenarioStep next, String answer);
    ScenarioStep getNext(String answer);
    boolean needUserAnswer();
}
