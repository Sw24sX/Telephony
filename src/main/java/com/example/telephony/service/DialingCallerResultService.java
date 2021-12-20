package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Dialing;
import com.example.telephony.domain.DialingCallerResult;
import com.example.telephony.repository.DialingCallerResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialingCallerResultService {
    private final DialingCallerResultRepository dialingCallerResultRepository;

    public DialingCallerResultService(DialingCallerResultRepository dialingCallerResultRepository) {
        this.dialingCallerResultRepository = dialingCallerResultRepository;
    }

    public DialingCallerResult create(Caller caller, Dialing dialing, List<String> answers) {
        DialingCallerResult dialingCallerResult = new DialingCallerResult();
        dialingCallerResult.setDialing(dialing);
        dialingCallerResult.setCaller(caller);
        dialingCallerResult.setAnswers(answers);
        dialingCallerResult.setHoldOn(false);
        return dialingCallerResultRepository.save(dialingCallerResult);
    }

    public DialingCallerResult createHoldOn(Caller caller, Dialing dialing) {
        DialingCallerResult dialingCallerResult = new DialingCallerResult();
        dialingCallerResult.setDialing(dialing);
        dialingCallerResult.setCaller(caller);
        dialingCallerResult.setHoldOn(true);
        return dialingCallerResultRepository.save(dialingCallerResult);
    }
}
