package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.file.CallersBaseParseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CallerBaseService {
    private final CallerBaseRepository callerBaseRepository;
    private final CallerRepository callerRepository;
    private final CallersBaseParseService callersBaseParseService;

    public CallerBaseService(CallerBaseRepository callerBaseRepository, CallerRepository callerRepository,
                             CallersBaseParseService callersBaseParseService) {
        this.callerBaseRepository = callerBaseRepository;
        this.callerRepository = callerRepository;
        this.callersBaseParseService = callersBaseParseService;
    }

    public List<CallersBase> getAll() {
        return callerBaseRepository.findAll();
    }

    public CallersBase getById(Long id) {
        CallersBase callersBase = callerBaseRepository.findById(id).orElse(null);
        if (callersBase == null) {
            throw new EntityNotFoundException(String.format(ExceptionMessage.CALLERS_BASE_NOT_FOUND.getMessage(), id));
        }
        return callersBase;
    }

    public CallersBase create(CallersBase callersBase) {
        callersBase.setCallers(callerRepository.saveAll(callersBase.getCallers()));
        return callerBaseRepository.save(callersBase);
    }

    public CallersBase update(Long id, CallersBase callersBase) {
        CallersBase callersBaseDb = getById(id);
        callersBaseDb.setName(callersBase.getName());
        callersBaseDb.setConfirmed(callersBase.isConfirmed() || callersBaseDb.isConfirmed());
        callersBaseDb.setVariablesList(callersBase.getVariablesList());
        return callerBaseRepository.save(callersBaseDb);
    }

    public void deleteCallersBase(Long id) {
        CallersBase callersBase = getById(id);
        callerBaseRepository.delete(callersBase);
    }

    public CallersBase uploadFromExelFile(MultipartFile multipartFile, String name){
        CallersBase callersBase = callersBaseParseService.parseExelToCallersBase(getInputStream(multipartFile), name);
        CallersBase a = callerBaseRepository.findById(callersBase.getId()).orElse(null);
        return callersBase;
    }

    private InputStream getInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }
}
