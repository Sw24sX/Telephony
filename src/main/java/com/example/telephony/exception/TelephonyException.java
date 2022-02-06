package com.example.telephony.exception;

public class TelephonyException extends RuntimeException {
    private static final long serialVersionUID = -4505819924771635158L;
    private String telephonyMessage;

    public TelephonyException(String message) {
        super(message);
        this.telephonyMessage = message;
    }

    public TelephonyException(Throwable cause, String telephonyMessage) {
        super(cause);
        this.telephonyMessage = telephonyMessage;
    }

    public TelephonyException(Throwable cause) {
        super(cause);
    }

    public String getTelephonyMessage() {
        return telephonyMessage;
    }
}