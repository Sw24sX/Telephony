package com.example.telephony.service.scenario.dialing;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingStatistic;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.DialingException;
import com.example.telephony.repository.DialingRepository;
import com.example.telephony.repository.DialingStatisticRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DialingManager {
    private final Map<Dialing, DialingState> countCallersNotDialed;
    private final DialingRepository dialingRepository;
    private final DialingStatisticRepository dialingStatisticRepository;

    public DialingManager(DialingRepository dialingRepository, DialingStatisticRepository dialingStatisticRepository) {
        this.dialingRepository = dialingRepository;
        this.dialingStatisticRepository = dialingStatisticRepository;
        countCallersNotDialed = new HashMap<>();
    }

    public void addDialing(Dialing dialing, Integer count) {
        if (countCallersNotDialed.containsKey(dialing)) {
            throw new DialingException(ExceptionMessage.DIALING_ALREADY_ADDED_TO_DIALING_MANAGER.getMessage());
        }

        DialingState state = new DialingState();
        state.setCountCallersNotDialed(count);
        state.setStartDate(new Date());
        countCallersNotDialed.put(dialing, state);
    }

    public void endDialCaller(Dialing dialing) {
        if (!countCallersNotDialed.containsKey(dialing)) {
            throw new DialingException(ExceptionMessage.DIALING_NOT_ADDED_TO_DIALING_MANAGER.getMessage());
        }

        Integer currentCount = countCallersNotDialed.get(dialing).getCountCallersNotDialed();
        int lastCallerNumber = 1;
        if (currentCount == lastCallerNumber) {
            dialing.setStatus(DialingStatus.DONE);
            dialingRepository.save(dialing);
            dialingStatisticRepository.save(createDialingStatistic(countCallersNotDialed.get(dialing), dialing));
            countCallersNotDialed.remove(dialing);
            return;
        }

        DialingState state = countCallersNotDialed.get(dialing);
        state.setCountCallersNotDialed(currentCount - 1);
        countCallersNotDialed.put(dialing, state);
    }

    private DialingStatistic createDialingStatistic(DialingState state, Dialing dialing) {
        DialingStatistic statistic = new DialingStatistic();
        statistic.setDialing(dialing);
        statistic.setStartDate(state.getStartDate());
        statistic.setEndDate(new Date());
        return statistic;
    }
}
