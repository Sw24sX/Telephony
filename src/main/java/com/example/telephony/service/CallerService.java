package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.repository.CallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallerService {
    private final CallerRepository callerRepository;

    @Autowired
    public CallerService(CallerRepository callerRepository) {
        this.callerRepository = callerRepository;
    }

    public List<Caller> getAll() {
        return callerRepository.findAll();
    }

    public Caller getById(Long id) {
        return callerRepository.findById(id).orElse(null);
    }

    public Caller create(Caller caller) {
        return callerRepository.save(caller);
    }

    public List<Caller> create(List<Caller> callers) {
        return callerRepository.saveAll(callers);
    }

    public Caller update(Long id, Caller caller) {
        Caller callerDb = getById(id);
        caller.setId(callerDb.getId());
        return callerRepository.save(caller);
    }

    public void delete(Long id) {
        Caller caller = getById(id);
        callerRepository.delete(caller);
    }
}
