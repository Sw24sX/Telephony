package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.enums.SpeechVoice;
import com.example.telephony.service.scenario.steps.BaseScenarioStep;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class ScenarioPreparationService {
    private final GenerationSoundsService generationSoundsService;

    public ScenarioPreparationService(GenerationSoundsService generationSoundsService) {
        this.generationSoundsService = generationSoundsService;
    }

    public Map<ScenarioStep, GeneratedSound> voiceOverScenarioByCaller(ScenarioStep firstStep, Caller caller) {
        Map<ScenarioStep, GeneratedSound> result = new HashMap<>();

        Queue<ScenarioStep> steps = new LinkedList<>();
        steps.add(firstStep);

        while(!steps.isEmpty()) {
            BaseScenarioStep current = (BaseScenarioStep) steps.poll();
            steps.addAll(current.getAllVariantsNext());

//            String question = current.getScenarioNode().getData().getQuestion();
            // TODO: 16.12.2021
            String question = "";
            if (StringUtils.isBlank(question)) {
                continue;
            }

            GeneratedSound sound = generationSoundsService.textToFile(question, SpeechVoice.IRINA);
            result.put(current, sound);
        }

        return result;
    }
}
