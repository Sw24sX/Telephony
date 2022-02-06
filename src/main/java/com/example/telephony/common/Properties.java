package com.example.telephony.common;

import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import org.springframework.core.env.Environment;

public final class Properties {
    private Properties() {
    }

    public static String getProperty(Environment environment, String propertyName) {
        String result = environment.getProperty(propertyName);
        if (result == null) {
            throw new TelephonyException(String.format(ExceptionMessage.PROPERTY_NOT_FOUND.getMessage(), propertyName));
        }
        return result;
    }
}
