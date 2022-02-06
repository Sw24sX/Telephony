package com.example.telephony.service;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.enums.DialingResultHoldOnMessages;
import com.example.telephony.repository.DialingCallerResultRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DialingCallerResultService {
    private final DialingCallerResultRepository dialingCallerResultRepository;

    public DialingCallerResultService(DialingCallerResultRepository dialingCallerResultRepository) {
        this.dialingCallerResultRepository = dialingCallerResultRepository;
    }

    public DialingCallerResult create(Caller caller, Dialing dialing, List<String> answers, Date startCall) {
        DialingCallerResult dialingCallerResult = new DialingCallerResult();
        dialingCallerResult.setDialing(dialing);
        dialingCallerResult.setCaller(caller);
        dialingCallerResult.setAnswers(answers);
        dialingCallerResult.setHoldOn(false);
        dialingCallerResult.setStatus(DialCallerStatus.CORRECT);
        dialingCallerResult.setStartCall(startCall);
        dialingCallerResult.setEndCall(new Date());
        return dialingCallerResultRepository.save(dialingCallerResult);
    }

    public DialingCallerResult createHoldOn(Caller caller, Dialing dialing, DialingResultHoldOnMessages message, DialCallerStatus status) {
        DialingCallerResult dialingCallerResult = new DialingCallerResult();
        dialingCallerResult.setDialing(dialing);
        dialingCallerResult.setCaller(caller);
        dialingCallerResult.setHoldOn(true);
        dialingCallerResult.setMessage(message.getMessage());
        dialingCallerResult.setStatus(status);
        dialingCallerResult.setStartCall(new Date());
        dialingCallerResult.setEndCall(new Date());
        return dialingCallerResultRepository.save(dialingCallerResult);
    }
}
