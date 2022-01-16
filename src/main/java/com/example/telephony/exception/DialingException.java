package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DialingException extends TelephonyException {
    private static final long serialVersionUID = -2244338693804809498L;

    public DialingException(String message) {
        super(message);
    }
}
