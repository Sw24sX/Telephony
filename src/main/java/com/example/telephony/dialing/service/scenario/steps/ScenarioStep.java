package com.example.telephony.dialing.service.scenario.steps;

import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.tts.persistance.domain.GeneratedSound;

import java.util.List;

public interface ScenarioStep {
    Playback execute(String channelId, GeneratedSound sound);
    void setNext(ScenarioStep next, String answer);
    ScenarioStep getNext(String answer);
    boolean needUserAnswer();
    List<ScenarioStep> getAllVariantsNext();
}
