package com.example.text.to.speech.service.config;

import com.example.text.to.speech.service.service.SoxReformat;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
public class SoxReformatConfig {
    private final Environment environment;

    public SoxReformatConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SoxReformat soxReformat() {
        return new SoxReformat(environment);
    }
}
