package com.example.telephony.tts.config;

import com.example.telephony.tts.service.engine.TTSEngineManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
public class TTSEngineManagerConfig {
    private final Environment environment;

    public TTSEngineManagerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TTSEngineManager ttsEngineManager() {
        return new TTSEngineManager(environment);
    }
}
