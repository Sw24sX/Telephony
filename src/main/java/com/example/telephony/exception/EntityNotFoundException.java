package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends TelephonyException {
    private static final long serialVersionUID = 2766327252015741145L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
