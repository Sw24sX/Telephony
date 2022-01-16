package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MappingException extends TelephonyException {
    private static final long serialVersionUID = 8793104720708365688L;

    public MappingException(String message) {
        super(message);
    }
}
