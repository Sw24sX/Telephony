package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.ARI;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.domain.scenario.ScenarioEdge;
import com.example.telephony.domain.scenario.ScenarioNode;
import com.example.telephony.enums.messages.ScenarioExceptionMessages;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.asterisk.AsteriskHelper;
import com.example.telephony.service.scenario.steps.*;

import java.util.LinkedList;
import java.util.Queue;

public class ScenarioBuilder {
    private final AsteriskHelper asteriskHelper;
    private ScenarioStep startStep;

    private ScenarioBuilder(AsteriskHelper asteriskHelper) {
        this.asteriskHelper = asteriskHelper;
    }

    public static ScenarioStep build(Scenario scenario, AsteriskHelper asteriskHelper) {
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder(asteriskHelper);

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
        StartStep result = new StartStep(node, asteriskHelper);
        this.startStep = result;
        return result;
    }

    private BaseScenarioStep createScenarioStep(ScenarioNode node) {
        switch (node.getType()){
            case REPLICA:
                return new SoundStep(node, asteriskHelper);
            case FINISH:
                return new EndStep(node, asteriskHelper);
            default:
                throw new TelephonyException(ScenarioExceptionMessages.START_SCENARIO_TYPE_CAN_BE_ONLY_ONE.name());
        }
    }
}
