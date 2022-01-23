package com.example.telephony.exception;

import com.example.telephony.enums.exception.messages.ChartExceptionMessages;

public class ChartException extends TelephonyException{
    private static final long serialVersionUID = 2779779503119287997L;

    public ChartException(ChartExceptionMessages message) {
        super(message.getMessage());
    }
}
