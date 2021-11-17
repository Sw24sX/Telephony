package com.example.telephony.service;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.service.file.CallersBaseParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CallerBaseService {
    private final CallerBaseRepository callerBaseRepository;
    private final CallerRepository callerRepository;

    public CallerBaseService(CallerBaseRepository callerBaseRepository, CallerRepository callerRepository) {
        this.callerBaseRepository = callerBaseRepository;
        this.callerRepository = callerRepository;
    }

    public List<CallersBase> getAll() {
        return callerBaseRepository.findAllByConfirmedIs(true);
    }

    public CallersBase getById(Long id) {
        CallersBase callersBase = callerBaseRepository.findById(id).orElse(null);
        if (callersBase == null) {
            throw new EntityNotFoundException(String.format(ExceptionMessage.CALLERS_BASE_NOT_FOUND.getMessage(), id));
        }
        return callersBase;
    }

    public CallersBase update(Long id, CallersBase callersBase) {
        CallersBase callersBaseDb = getById(id);
        callersBaseDb.setName(callersBase.getName());
        callersBaseDb.setConfirmed(callersBase.isConfirmed() || callersBaseDb.isConfirmed());
        callersBaseDb.setVariablesList(callersBase.getVariablesList());
        return callerBaseRepository.save(callersBaseDb);
    }

    public void deleteCallersBase(Long id) {
        //todo
//        CallersBase callersBase = getById(id);
//        callerRepository.deleteAll(callersBase.getCallers());
//        callerBaseRepository.deleteById(id);
    }

    public CallersBase uploadFromExelFile(MultipartFile multipartFile, String name) {
        CallersBaseParser callersBaseParser = new CallersBaseParser(getInputStream(multipartFile));
        CallersBase callersBase = callersBaseParser.parseExelToCallersBase();
        callersBase.setName(name);

        CallersBase result = callerBaseRepository.save(callersBase);
        return result;
    }

    private InputStream getInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }
}
