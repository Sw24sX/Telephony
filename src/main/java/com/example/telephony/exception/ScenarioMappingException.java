package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScenarioMappingException extends TelephonyException {
    private static final long serialVersionUID = -4105182708746150559L;

    public ScenarioMappingException(String message) {
        super(message);
    }
}
