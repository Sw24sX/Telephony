package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ScenarioMappingException extends TelephonyException {
    public ScenarioMappingException(String message) {
        super(message);
    }
}
