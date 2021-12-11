package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.TTSService;
import com.example.telephony.service.scenario.dialing.steps.*;
import org.apache.commons.collections4.QueueUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Queue;
import java.util.Set;

public class ScenarioBuilder {
    private final ARI ari;
    private final TTSService ttsService;
    private ScenarioStep startStep;

    private ScenarioBuilder(ARI ari, TTSService ttsService) {
        this.ari = ari;
        this.ttsService = ttsService;
    }

    public static ScenarioStep build(Scenario scenario, ARI ari, TTSService ttsService) {
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder(ari, ttsService);

        Queue<BaseScenarioStep> stepsWithoutChild = QueueUtils.emptyQueue();
        Set<Long> alreadyCreatedSteps = SetUtils.emptySet();

        stepsWithoutChild.add(scenarioBuilder.createStartStep(scenario.getRoot()));
        alreadyCreatedSteps.add(scenario.getRoot().getId());

        while(!stepsWithoutChild.isEmpty()) {
            BaseScenarioStep current = stepsWithoutChild.poll();

            for (ScenarioEdge edge : current.getScenarioNode().getChildEdges()) {
                ScenarioNode target = edge.getTarget();
                if (alreadyCreatedSteps.contains(target.getId())) {
                    continue;
                }

                BaseScenarioStep step = scenarioBuilder.createScenarioStep(target);
                current.setNext(step, edge.getAnswerKey());

                alreadyCreatedSteps.add(target.getId());
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

//        GeneratedSound generatedSound = ttsService.textToFile(current.getData().getQuestion(), SpeechVoice.IRINA);
//        current.getData().setSoundPath(generatedSound.getUri());

        return null;
    }
}
