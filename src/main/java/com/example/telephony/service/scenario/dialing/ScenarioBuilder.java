package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.SpeechVoice;
import com.example.telephony.service.TTSService;
import com.example.telephony.service.scenario.dialing.steps.EndStep;
import com.example.telephony.service.scenario.dialing.steps.ScenarioStep;
import com.example.telephony.service.scenario.dialing.steps.SoundStep;
import com.example.telephony.service.scenario.dialing.steps.StartStep;

public class ScenarioBuilder {
    private ScenarioStep firstStep;
    private ScenarioStep lastStep;

    private ScenarioBuilder() {
    }

    private void addScenarioStep(ScenarioStep scenarioStep) {
        if(lastStep != null) {
            lastStep.setNext(scenarioStep);
            lastStep = scenarioStep;
        }

        if(firstStep == null) {
            firstStep = scenarioStep;
            lastStep = scenarioStep;
        }
    }

    public static ScenarioStep build(Scenario scenario, ARI ari, TTSService ttsService) {
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder();
        //todo: may be not only one children

        ScenarioNode current = scenario.getRoot();
        while(current != null) {
            switch (current.getType()){
                case START:
                    scenarioBuilder.addScenarioStep(new StartStep(current, ari));
                    current = getNextNode(current);
                    break;
                case REPLICA:
                    String soundPath = current.getData().getSoundPath();
                    if (soundPath == null || soundPath.isEmpty()) {
                        GeneratedSound generatedSound = ttsService.textToFile(current.getData().getQuestion(), SpeechVoice.IRINA);
                        current.getData().setSoundPath(generatedSound.getUri());
                    }
                    scenarioBuilder.addScenarioStep(new SoundStep(current, ari));
                    current = getNextNode(current);
                    break;
                case FINISH:
                    scenarioBuilder.addScenarioStep(new EndStep(current, ari));
                    current = getNextNode(current);
                    break;
            }
        }

        return scenarioBuilder.firstStep;
    }

    private static ScenarioNode getNextNode(ScenarioNode current) {
        current.getChildEdges();
        //todo: fix this
//        if (current.getChildren().size() == 0) {
//            return null;
//        }
//        return current.getChildren().get(0);
        return null;
    }
}
