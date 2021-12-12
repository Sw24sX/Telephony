package com.example.telephony.configuration;

import com.example.telephony.service.GenerationSoundsService;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScenarioManagerConfig {
    private final GenerationSoundsService generationSoundsService;

    public ScenarioManagerConfig(GenerationSoundsService generationSoundsService) {
        this.generationSoundsService = generationSoundsService;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ScenarioManager scenarioCall() {
        return new ScenarioManager(generationSoundsService);
    }
}
