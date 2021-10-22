package com.example.telephony.exception;

public class EntityNotFoundException extends TelephonyException {
    private static final long serialVersionUID = 2766327252015741145L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
