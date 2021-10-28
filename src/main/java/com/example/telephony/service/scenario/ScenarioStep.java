package com.example.telephony.service.scenario;

import ch.loway.oss.ari4java.generated.models.Channel;

public interface ScenarioStep {
    void execute(Channel channel);
    void setNext(ScenarioStep next);
}
