package com.example.telephony.service;

import com.example.telephony.domain.CallStatistic;
import com.example.telephony.domain.Digit;
import com.example.telephony.repository.CallStatisticRepository;
import com.example.telephony.repository.DigitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallStatisticService {
    private final CallStatisticRepository callStatisticRepository;
    private final DigitRepository digitRepository;

    public CallStatisticService(CallStatisticRepository callStatisticRepository, DigitRepository digitRepository) {
        this.callStatisticRepository = callStatisticRepository;
        this.digitRepository = digitRepository;
    }

    public List<CallStatistic> getAll() {
        return callStatisticRepository.findAll();
    }

    public CallStatistic getById(Long id) {
        return callStatisticRepository.findById(id).orElse(null);
    }

    public CallStatistic create(CallStatistic callStatistic) {
        return callStatisticRepository.save(callStatistic);
    }

    public CallStatistic update(CallStatistic callStatistic) {
        return callStatisticRepository.save(callStatistic);
    }

    public CallStatistic addDigit(CallStatistic callStatistic, String value) {
        CallStatistic callStatisticDb = getById(callStatistic.getId());
        Digit digit = new Digit();
        digit.setDigit(value);
        digit.setCallStatistic(callStatisticDb);
        digit = digitRepository.save(digit);
        callStatisticDb.getDigits().add(digit);
        return callStatisticRepository.save(callStatisticDb);
    }

    public CallStatistic addDigit(String channel, String digit) {
        CallStatistic callStatistic = callStatisticRepository.findByChannel(channel);
        return addDigit(callStatistic, digit);
    }
}
