package com.example.telephony.service;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.CallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CallerService {
    private final CallerRepository callerRepository;

    @Autowired
    public CallerService(CallerRepository callerRepository) {
        this.callerRepository = callerRepository;
    }

    public Caller getById(Long id) {
        return callerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.CALLER_NOT_FOUND.getMessage(), id)));
    }

    public void delete(Long id) {
        Caller caller = getById(id);
        callerRepository.delete(caller);
    }

    public Page<Caller> getPageCallerByCallerBaseId(Long callerBaseId, int number, int size, boolean onlyInvalid) {
        Pageable pageable = PageRequest.of(number, size);
        boolean validColumn = !onlyInvalid;
        return onlyInvalid ?
                callerRepository.findAllByCallersBase_idAndAndValid(callerBaseId, validColumn, pageable) :
                callerRepository.findAllByCallersBase_id(callerBaseId, pageable);
    }

    public int getCountInvalidCallers(Long id) {
        return callerRepository.getCountInvalidCallers(id);
    }

    public String getCallerNumber(Long callerId) {
        return callerRepository.getCallerNumber(callerId);
    }
}
