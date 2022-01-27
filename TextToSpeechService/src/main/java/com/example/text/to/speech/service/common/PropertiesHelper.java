package com.example.text.to.speech.service.common;

import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import com.example.text.to.speech.service.enums.ExceptionMessages;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import org.springframework.core.env.Environment;

public class PropertiesHelper {
    private PropertiesHelper() {
    }

    public static String getApplicationProperty(CustomApplicationProperty property, Environment environment) {
        String result = environment.getProperty(property.getName());
        if (result == null) {
            throw new TextToSpeechException(ExceptionMessages.PROPERTY_NOT_FOUND, property.getName());
        }

        return environment.getProperty(property.getName());
    }

    public static String getSystemEnvironment(String name) {
        return System.getenv(name);
    }
}
