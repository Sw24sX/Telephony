package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.ARIException;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Caller;
import com.example.telephony.service.asterisk.Asterisk;
import com.example.telephony.service.asterisk.MessageCallBackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AsteriskService {
    private final Asterisk asterisk;
    private final CallerService callerService;

    @Autowired
    public AsteriskService(Environment environment, CallerService callerService) throws ARIException {
        this.asterisk = Asterisk.create(environment);
        this.callerService = callerService;
        asterisk.setMessageCallBack(new MessageCallBackImpl());
    }

    public void callByCaller(Caller caller) throws RestException {
        asterisk.call(caller);
    }

    public void callAll() throws RestException {
        for (Caller caller : callerService.getAll()) {
            asterisk.call(caller);
        }
    }
}
