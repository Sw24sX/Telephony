package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallerBaseService {
    private final CallerBaseRepository callerBaseRepository;
    private final CallerService callerService;
    private final CallerRepository callerRepository;

    public CallerBaseService(CallerBaseRepository callerBaseRepository,
                             CallerService callerService, CallerRepository callerRepository) {
        this.callerBaseRepository = callerBaseRepository;
        this.callerService = callerService;
        this.callerRepository = callerRepository;
    }

    public List<CallersBase> getAll() {
        return callerBaseRepository.findAll();
    }

    public CallersBase getById(Long id) {
        return callerBaseRepository.findById(id).orElse(null);
    }

    public CallersBase create(CallersBase callersBase) {
        List<Caller> alreadyCreates = callerRepository.findAllByNumberIn(
                getNumbersFromCallers(callersBase.getCallers()));
        if (!alreadyCreates.isEmpty()) {
            throw new TelephonyException(ExceptionMessage.CALLERS_ALREADY_CREATED.getMessage());
        }
        callersBase.setCallers(callerRepository.saveAll(callersBase.getCallers()));
        return callerBaseRepository.save(callersBase);
    }

    private List<String> getNumbersFromCallers(List<Caller> callers) {
        return callers.stream().map(Caller::getNumber).collect(Collectors.toList());
    }

    public CallersBase update(Long id, CallersBase callersBase) {
        CallersBase callersBaseDb = getById(id);

        List<Caller> alreadyCreates = callerRepository.findAllByNumberIn(
                getNumbersFromCallers(callersBase.getCallers()));
        if (alreadyCreates.size() != callersBase.getCallers().size()) {
            throw new TelephonyException(ExceptionMessage.CALLERS_NOT_CREATED.getMessage());
        }
        callersBase.setId(callersBaseDb.getId());
        callersBase.setCallers(alreadyCreates);
        return callerBaseRepository.save(callersBase);
    }

    public void deleteCallersBase(Long id) {
        CallersBase callersBase = getById(id);
        callerBaseRepository.delete(callersBase);
    }
}
