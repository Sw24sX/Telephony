package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.ARIException;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.asterisk.Asterisk;
import com.example.telephony.service.asterisk.MessageCallBackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CallerService {
    private final Asterisk asterisk;
    private final CallerRepository callerRepository;

    @Autowired
    public CallerService(CallerRepository callerRepository, Environment environment) throws ARIException {
        this.callerRepository = callerRepository;
        this.asterisk = Asterisk.create(environment);
        asterisk.setMessageCallBack(new MessageCallBackImpl());
    }
}
