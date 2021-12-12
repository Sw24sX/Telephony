package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.GenerationSoundsService;
import com.example.telephony.service.scenario.steps.*;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.Queue;

public class ScenarioBuilder {
    private final ARI ari;
    private final GenerationSoundsService generationSoundsService;
    private ScenarioStep startStep;

    private ScenarioBuilder(ARI ari, GenerationSoundsService generationSoundsService) {
        this.ari = ari;
        this.generationSoundsService = generationSoundsService;
    }

    public static ScenarioStep build(Scenario scenario, ARI ari, GenerationSoundsService generationSoundsService) {
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder(ari, generationSoundsService);

        Queue<BaseScenarioStep> stepsWithoutChild = new LinkedList<>();
        stepsWithoutChild.add(scenarioBuilder.createStartStep(scenario.getRoot()));

        while(!stepsWithoutChild.isEmpty()) {
            BaseScenarioStep current = stepsWithoutChild.poll();

            for (ScenarioEdge edge : current.getScenarioNode().getChildEdges()) {
                ScenarioNode target = edge.getTarget();
                BaseScenarioStep step = scenarioBuilder.createScenarioStep(target);
                current.setNext(step, edge.getAnswerKey());
                stepsWithoutChild.add(step);
            }
        }

        return scenarioBuilder.startStep;
    }

    private StartStep createStartStep(ScenarioNode node) {
        StartStep result = new StartStep(node, ari);
        this.startStep = result;
        return result;
    }

    private BaseScenarioStep createScenarioStep(ScenarioNode node) {
        switch (node.getType()){
            case REPLICA:
                String soundPath = StringUtils.isBlank(node.getData().getSoundPath()) ?
                        getPathForSoundText(node) : node.getData().getSoundPath();
                node.getData().setSoundPath(soundPath);
                return new SoundStep(node, ari);
            case FINISH:
                return new EndStep(node, ari);
            default:
                throw new TelephonyException(ScenarioExceptionMessages.START_SCENARIO_TYPE_CAN_BE_ONLY_ONE.name());
        }
    }

    private String getPathForSoundText(ScenarioNode node) {
        // TODO: 11.12.2021
        generationSoundsService.getAll();
        assert node != null;
//        GeneratedSound generatedSound = ttsService.textToFile(current.getData().getQuestion(), SpeechVoice.IRINA);
//        current.getData().setSoundPath(generatedSound.getUri());

        return null;
    }
}
