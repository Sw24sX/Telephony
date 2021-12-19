package com.example.telephony.service.scenario.steps;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.Playback;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundStep extends BaseScenarioStep {
    private Map<String, ScenarioStep> nextSteps;

    public SoundStep(ScenarioNode scenarioNode, ARI ari) {
        super(scenarioNode, ari);
        nextSteps = new HashMap<>();
    }

    @Override
    public Playback execute(String channelId, GeneratedSound sound) {
        String mediaUrl = String.format("sound:%s", sound.getUri());
        try {
            return ari.channels().play(channelId, mediaUrl).execute();
        } catch (RestException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    @Override
    public void setNext(ScenarioStep next, String answer) {
        if(nextSteps.containsKey(answer)) {
            String message = String.format(ScenarioExceptionMessages.ANSWER_BUTTON_ALREADY_EXIST.getMessage(), answer);
            throw new TelephonyException(message);
        }
        String answerKey = answer == null ? EMPTY_ANSWER : answer;
        nextSteps.put(answerKey, next);
    }

    @Override
    public ScenarioStep getNext(String answer) {
        String answerKey = answer == null ? EMPTY_ANSWER : answer;
        return nextSteps.get(answerKey);
    }

    @Override
    public List<ScenarioStep> getAllVariantsNext() {
        return new ArrayList<>(nextSteps.values());
    }
}
