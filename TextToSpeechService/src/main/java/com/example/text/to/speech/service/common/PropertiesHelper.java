package com.example.text.to.speech.service.common;

import com.example.text.to.speech.service.enums.ExceptionMessages;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import org.springframework.core.env.Environment;

public class PropertiesHelper {
    private PropertiesHelper() {
    }

    public static String getApplicationProperty(String name, Environment environment) {
        String property = environment.getProperty(name);
        if (property == null) {
            throw new TextToSpeechException(ExceptionMessages.PROPERTY_NOT_FOUND, name);
        }

        return environment.getProperty(name);
    }

    public static String geSystemEnvironment(String name) {
        return System.getenv(name);
    }
}
