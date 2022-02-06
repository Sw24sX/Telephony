package com.example.telephony.dialing.service;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.tts.persistance.domain.GeneratedSound;
import com.example.telephony.domain.scenario.ScenarioQuestion;
import com.example.telephony.domain.scenario.ScenarioQuestionPart;
import com.example.telephony.enums.exception.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.ScenarioBuildException;
import com.example.telephony.dialing.service.scenario.steps.BaseScenarioStep;
import com.example.telephony.dialing.service.scenario.steps.ScenarioStep;
import com.example.telephony.tts.service.GenerationSoundsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class ScenarioPreparationService {
    private final GenerationSoundsService generationSoundsService;

    public ScenarioPreparationService(GenerationSoundsService generationSoundsService) {
        this.generationSoundsService = generationSoundsService;
    }

    public Map<ScenarioStep, GeneratedSound> voiceOverScenarioByCaller(ScenarioStep firstStep, Caller caller) throws ScenarioBuildException {
        Map<ScenarioStep, GeneratedSound> result = new HashMap<>();

        Queue<ScenarioStep> steps = new LinkedList<>();
        steps.add(firstStep);

        while(!steps.isEmpty()) {
            BaseScenarioStep current = (BaseScenarioStep) steps.poll();
            steps.addAll(current.getAllVariantsNext());

            String question = addCallerVariables(current.getScenarioNode().getData().getQuestion(), callerVariablesToMap(caller));
            if (StringUtils.isBlank(question)) {
                continue;
            }

            GeneratedSound sound = generationSoundsService.textToGeneratedFile(question);
            result.put(current, sound);
        }

        return result;
    }

    private String addCallerVariables(ScenarioQuestion question, Map<String, String> variables) throws ScenarioBuildException {
        if (question == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (ScenarioQuestionPart part : question.getParts()) {
            if(!part.isVariable()) {
                builder.append(part.getQuestionPart());
                continue;
            }

            String variableName = part.getQuestionPart().replace("*", "");
            String variableValue = variables.get(variableName);
            if(StringUtils.isBlank(variableValue)) {
                String message = String.format(ScenarioExceptionMessages.CALLER_HAVE_NOT_VARIABLE.getMessage(), variableName);
                throw new ScenarioBuildException(message);
            }
            builder.append(variableValue);
        }
        return builder.toString();
    }

    private Map<String, String> callerVariablesToMap(Caller caller) {
        // TODO: 17.12.2021 use variable type
        return caller.getVariables().stream()
                .collect(Collectors
                            .toMap(variable -> variable.getTypeName().getCurrentName(), variable -> variable.getValue()));
    }
}
