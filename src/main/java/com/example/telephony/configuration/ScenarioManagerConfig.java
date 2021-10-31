package com.example.telephony.configuration;

import com.example.telephony.service.scenario.ScenarioManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScenarioManagerConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ScenarioManager scenarioCall() {
        return new ScenarioManager();
    }
}
