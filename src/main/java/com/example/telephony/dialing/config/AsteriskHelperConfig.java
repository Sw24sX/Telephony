package com.example.telephony.dialing.config;

import ch.loway.oss.ari4java.tools.ARIException;
import com.example.telephony.dialing.service.asterisk.AsteriskHelper;
import com.example.telephony.dialing.service.asterisk.MessageCallBack;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
public class AsteriskHelperConfig {
    private final Environment environment;
    private final MessageCallBack messageCallBack;

    public AsteriskHelperConfig(Environment environment, MessageCallBack messageCallBack) {
        this.environment = environment;
        this.messageCallBack = messageCallBack;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AsteriskHelper asteriskHelper() throws ARIException {
        return new AsteriskHelper(environment, messageCallBack);
    }
}
