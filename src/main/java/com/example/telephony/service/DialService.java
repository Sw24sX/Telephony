package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.ARIException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Dial;
import com.example.telephony.repository.DialRepository;
import com.example.telephony.service.asterisk.Asterisk;
import com.example.telephony.service.asterisk.MessageCallBackImpl;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialService {
    private final DialRepository dialRepository;
    private final Asterisk asterisk;

    public DialService(DialRepository dialRepository, Environment environment) throws ARIException {
        this.dialRepository = dialRepository;
        this.asterisk = Asterisk.create(environment);
        asterisk.setMessageCallBack(new MessageCallBackImpl());
    }

    public void startDialing() {
        //TODO
    }

    public void startDialing(Dial dial) {
        // TODO
    }

    public List<Dial> getAll() {
        //TODO
        return null;
    }

    public Dial getById(Long id) {
        //TODO
        return null;
    }

    public Dial create(Dial dial) {
        //TODO
        return null;
    }

    public Dial update(Dial dial) {
        //TODO
        return null;
    }

    public Dial addCallerToDial(Dial dial, Caller caller) {
        //TODO
        return null;
    }

    public Dial removeFromDial(Dial dial, Caller caller) {
        //TODO
        return null;
    }
}
