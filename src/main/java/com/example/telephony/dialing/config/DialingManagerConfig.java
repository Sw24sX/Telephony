package com.example.telephony.dialing.config;

import com.example.telephony.dialing.persistance.repository.DialingRepository;
import com.example.telephony.dialing.persistance.repository.DialingStatisticRepository;
import com.example.telephony.dialing.service.scenario.dialing.DialingManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DialingManagerConfig {
    private final DialingRepository dialingRepository;
    private final DialingStatisticRepository dialingStatisticRepository;

    public DialingManagerConfig(DialingRepository dialingRepository, DialingStatisticRepository dialingStatisticRepository) {
        this.dialingRepository = dialingRepository;
        this.dialingStatisticRepository = dialingStatisticRepository;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DialingManager dialingManager() {
        return new DialingManager(dialingRepository, dialingStatisticRepository);
    }
}
