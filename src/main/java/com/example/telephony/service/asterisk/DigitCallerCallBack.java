package com.example.telephony.service.asterisk;

import com.example.telephony.domain.Caller;
import com.example.telephony.repository.CallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class DigitCallerCallBack {
    private List<String> digitsCallers;
    private final Caller caller;

    public DigitCallerCallBack(Caller caller) {
        this.caller = caller;
        digitsCallers = new ArrayList<>();
    }

    public void addDigit(String digit) {
        digitsCallers.add(digit);
    }

    public Caller getCaller() {
        return caller;
    }

    public List<String> getDigits() {
        return digitsCallers;
    }
}
