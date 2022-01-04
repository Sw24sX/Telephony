package com.example.telephony.service.scenario.dialing;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.DialingException;
import com.example.telephony.repository.DialingRepository;

import java.util.HashMap;
import java.util.Map;

public class DialingManager {
    private final Map<Dialing, Integer> countCallersNotDialed;
    private final DialingRepository dialingRepository;

    public DialingManager(DialingRepository dialingRepository) {
        this.dialingRepository = dialingRepository;
        countCallersNotDialed = new HashMap<>();
    }

    public void addDialing(Dialing dialing, Integer count) {
        if (countCallersNotDialed.containsKey(dialing)) {
            throw new DialingException(ExceptionMessage.DIALING_ALREADY_ADDED_TO_DIALING_MANAGER.getMessage());
        }

        countCallersNotDialed.put(dialing, count);
    }

    public void endDialCaller(Dialing dialing) {
        if (!countCallersNotDialed.containsKey(dialing)) {
            throw new DialingException(ExceptionMessage.DIALING_NOT_ADDED_TO_DIALING_MANAGER.getMessage());
        }

        Integer currentCount = countCallersNotDialed.get(dialing);
        if (currentCount == 1) {
            countCallersNotDialed.remove(dialing);
            dialing.setStatus(DialingStatus.DONE);
            dialingRepository.save(dialing);
            return;
        }

        countCallersNotDialed.put(dialing, currentCount - 1);
    }
}
