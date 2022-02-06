package com.example.telephony.dialing.config;

import com.example.telephony.dialing.service.DialingCallerResultService;
import com.example.telephony.tts.service.GenerationSoundsService;
import com.example.telephony.dialing.service.scenario.dialing.DialingManager;
import com.example.telephony.dialing.service.scenario.manager.ScenarioManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScenarioManagerConfig {
    private final GenerationSoundsService generationSoundsService;
    private final DialingCallerResultService dialingCallerResultService;
    private final DialingManager dialingManager;

    public ScenarioManagerConfig(GenerationSoundsService generationSoundsService,
                                 DialingCallerResultService dialingCallerResultService, DialingManager dialingManager) {
        this.generationSoundsService = generationSoundsService;
        this.dialingCallerResultService = dialingCallerResultService;
        this.dialingManager = dialingManager;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ScenarioManager scenarioCall() {
        return new ScenarioManager(generationSoundsService, dialingCallerResultService, dialingManager);
    }
}
