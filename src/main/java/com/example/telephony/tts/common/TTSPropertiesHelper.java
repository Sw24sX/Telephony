package com.example.telephony.tts.common;

import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.tts.persistance.enums.TTSProperty;
import org.springframework.core.env.Environment;

public final class TTSPropertiesHelper {
    private TTSPropertiesHelper() {
    }

    /**
     * Get property from application.properties
     * @param propertyName Property name
     * @param environment Spring environment
     * @return Property value or TelephonyException
     */
    public static String getProperty(String propertyName, Environment environment) {
        String result = environment.getProperty(propertyName);
        if (result == null) {
            throw new TelephonyException(String.format(ExceptionMessage.PROPERTY_NOT_FOUND.getMessage(), propertyName));
        }
        return result;
    }

    /**
     * Get property from application.properties
     * @param property Enum property name
     * @param environment Spring environment
     * @return Property value or TelephonyException
     */
    public static String getProperty(TTSProperty property, Environment environment) {
        return getProperty(property.getProperty(), environment);
    }

    /**
     * Get system environment property
     * @param name Property name
     * @return Property value
     */
    public static String getSystemProperty(String name) {
        return System.getenv(name);
    }
}
