package com.example.telephony.common;

import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import org.springframework.core.env.Environment;

public final class PropertiesHelper {
    private PropertiesHelper() {
    }

    public static String getProperty(Environment environment, String propertyName) {
        String result = environment.getProperty(propertyName);
        if (result == null) {
            throw new TelephonyException(String.format(ExceptionMessage.PROPERTY_NOT_FOUND.getMessage(), propertyName));
        }
        return result;
    }

    public static String getSystemProperty(String name) {
        return System.getenv(name);
    }
}
