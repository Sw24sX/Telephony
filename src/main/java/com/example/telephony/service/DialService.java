package com.example.telephony.service;

import ch.loway.oss.ari4java.tools.ARIException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Dial;
import com.example.telephony.repository.DialRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialService {
    private final DialRepository dialRepository;
    private final CallerService callerService;

    public DialService(DialRepository dialRepository, CallerService callerService, Environment environment) throws ARIException {
        this.dialRepository = dialRepository;
        this.callerService = callerService;
    }

    public void startDialing() {
        //TODO
    }

    public List<Dial> getAll() {
        return dialRepository.findAll();
    }

    public Dial getById(Long id) {
        return dialRepository.findById(id).orElse(null);
    }

    public Dial create(Dial dial) {
        return dialRepository.save(dial);
    }

    public Dial update(Long id, Dial dial) {
        //TODO save new numbers
        Dial dialDb = getById(id);
        dial.setId(dialDb.getId());
        return dialRepository.save(dial);
    }

    public Dial addCallerToDial(Long dialId, Long callerId) {
        Dial dial = getById(dialId);
        Caller caller = callerService.getById(callerId);
        dial.getCallers().add(caller);
        return dialRepository.save(dial);
    }

    public Dial removeFromDial(Dial dial, Caller caller) {
        //TODO
        return null;
    }
}
