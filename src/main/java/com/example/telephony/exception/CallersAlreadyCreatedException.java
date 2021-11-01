package com.example.telephony.exception;

import com.example.telephony.domain.Caller;
import com.example.telephony.enums.ExceptionMessage;

import java.util.List;

public class CallersAlreadyCreatedException extends TelephonyException{
    private final List<Caller> callers;

    public CallersAlreadyCreatedException(List<Caller> callers) {
        super(ExceptionMessage.CALLERS_ALREADY_CREATED.getMessage() + "\n" + callers.toString());
        this.callers = callers;
    }

    public List<Caller> getCallers() {
        return callers;
    }
}
