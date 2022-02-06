package com.example.telephony.dialing.service;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.dialing.persistance.model.Dialing;
import com.example.telephony.dialing.persistance.model.DialingCallerResult;
import com.example.telephony.dialing.persistance.enums.DialCallerStatus;
import com.example.telephony.dialing.persistance.enums.DialingResultHoldOnMessages;
import com.example.telephony.dialing.persistance.repository.DialingCallerResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DialingCallerResultService {
    private final DialingCallerResultRepository dialingCallerResultRepository;

    public DialingCallerResultService(DialingCallerResultRepository dialingCallerResultRepository) {
        this.dialingCallerResultRepository = dialingCallerResultRepository;
    }

    public DialingCallerResult create(Caller caller, Dialing dialing, List<String> answers, LocalTime startCall) {
        DialingCallerResult dialingCallerResult = new DialingCallerResult();
        dialingCallerResult.setDialing(dialing);
        dialingCallerResult.setCaller(caller);
        dialingCallerResult.setAnswers(answers);
        dialingCallerResult.setHoldOn(false);
        dialingCallerResult.setStatus(DialCallerStatus.CORRECT);
        dialingCallerResult.setStartCall(startCall);
        dialingCallerResult.setEndCall(LocalTime.now());
        return dialingCallerResultRepository.save(dialingCallerResult);
    }

    public DialingCallerResult createHoldOn(Caller caller, Dialing dialing, DialingResultHoldOnMessages message, DialCallerStatus status) {
        DialingCallerResult dialingCallerResult = new DialingCallerResult();
        dialingCallerResult.setDialing(dialing);
        dialingCallerResult.setCaller(caller);
        dialingCallerResult.setHoldOn(true);
        dialingCallerResult.setMessage(message.getMessage());
        dialingCallerResult.setStatus(status);
        dialingCallerResult.setStartCall(LocalTime.now());
        dialingCallerResult.setEndCall(LocalTime.now());
        return dialingCallerResultRepository.save(dialingCallerResult);
    }
}
