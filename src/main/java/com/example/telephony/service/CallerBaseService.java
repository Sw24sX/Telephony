package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.ARIException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.repository.CallerBaseRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallerBaseService {
    private final CallerBaseRepository callerBaseRepository;
    private final CallerService callerService;

    public CallerBaseService(CallerBaseRepository callerBaseRepository, CallerService callerService, Environment environment) throws ARIException {
        this.callerBaseRepository = callerBaseRepository;
        this.callerService = callerService;
    }

    public void startDialing() {
        //TODO
    }

    public List<CallersBase> getAll() {
        return callerBaseRepository.findAll();
    }

    public CallersBase getById(Long id) {
        return callerBaseRepository.findById(id).orElse(null);
    }

    public CallersBase create(CallersBase callersBase) {
        return callerBaseRepository.save(callersBase);
    }

    public CallersBase update(Long id, CallersBase callersBase) {
        //TODO save new numbers
        CallersBase callersBaseDb = getById(id);
        callersBase.setId(callersBaseDb.getId());
        return callerBaseRepository.save(callersBase);
    }

    public CallersBase addCallerToDial(Long dialId, Long callerId) {
        CallersBase callersBase = getById(dialId);
        Caller caller = callerService.getById(callerId);
        callersBase.getCallers().add(caller);
        return callerBaseRepository.save(callersBase);
    }

    public CallersBase removeFromDial(CallersBase callersBase, Caller caller) {
        //TODO
        return null;
    }
}
