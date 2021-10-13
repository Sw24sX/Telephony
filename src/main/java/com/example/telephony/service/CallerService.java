package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.ARIException;
import com.example.telephony.domain.Caller;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.asterisk.Asterisk;
import com.example.telephony.service.asterisk.MessageCallBackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Caller> getAll() {
        return callerRepository.findAll();
    }

    public Caller getById(Long id) {
        return callerRepository.findById(id).orElse(null);
    }

    public Caller create(Caller caller) {
        return callerRepository.save(caller);
    }

    public Caller update(Long id, Caller caller) {
        Caller callerDb = getById(id);
        caller.setId(callerDb.getId());
        return callerRepository.save(caller);
    }

    public void delete(Long id) {
        Caller caller = getById(id);
        callerRepository.delete(caller);
    }
}
