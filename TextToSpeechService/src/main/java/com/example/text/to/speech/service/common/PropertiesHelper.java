package com.example.text.to.speech.service.common;

import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import com.example.text.to.speech.service.enums.ExceptionMessages;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import org.springframework.core.env.Environment;

/**
 * Common class for getting system or application property
 */
public class PropertiesHelper {
    private PropertiesHelper() {
    }

    /**
     * Get application property by name
     * @param property Enum with name properties
     * @param environment Spring environment
     * @return Property value
     */
    public static String getApplicationProperty(CustomApplicationProperty property, Environment environment) {
        String result = environment.getProperty(property.getName());
        if (result == null) {
            throw new TextToSpeechException(ExceptionMessages.PROPERTY_NOT_FOUND, property.getName());
        }

        return environment.getProperty(property.getName());
    }

    /**
     * Get system environment property by name
     * @param name Property name
     * @return Property value
     */
    public static String getSystemEnvironment(String name) {
        return System.getenv(name);
    }
}
