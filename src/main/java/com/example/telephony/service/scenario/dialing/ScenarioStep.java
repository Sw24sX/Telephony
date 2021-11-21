package com.example.telephony.service.scenario.dialing;

import ch.loway.oss.ari4java.generated.models.Channel;
import ch.loway.oss.ari4java.generated.models.Event;
import ch.loway.oss.ari4java.generated.models.Playback;
import com.example.telephony.service.asterisk.AsteriskEvent;

public interface ScenarioStep {
    Playback execute(Channel channel);
    void setNext(ScenarioStep next);
    ScenarioStep getNext();
}
