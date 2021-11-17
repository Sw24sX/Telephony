package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallerVariable;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.repository.VariablesTypeNameRepository;
import com.example.telephony.service.file.CallersBaseParseService;
import com.example.telephony.service.file.CallersBaseParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class CallerBaseService {
    private final CallerBaseRepository callerBaseRepository;

    public CallerBaseService(CallerBaseRepository callerBaseRepository) {
        this.callerBaseRepository = callerBaseRepository;
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

    public CallersBase create(CallersBase callersBase1) {
//        callersBase.setCallers(callerRepository.saveAll(callersBase.getCallers()));
//        return callerBaseRepository.save(callersBase);

        CallersBase callersBase = new CallersBase();
        callersBase.setName("test");

        VariablesTypeName variablesTypeName = new VariablesTypeName();
        variablesTypeName.setType(VariablesType.BOOLEAN);
        variablesTypeName.setCallersBase(callersBase);
        variablesTypeName.setTableName("test");
        variablesTypeName.setCurrentName("test");

        callersBase.setVariablesList(List.of(variablesTypeName));

        Caller caller = new Caller();
        caller.setCallersBase(callersBase);

        CallerVariable callerVariable = new CallerVariable();
        callerVariable.setCaller(caller);
        callerVariable.setValid(true);
        callerVariable.setPhoneColumn(true);
        callerVariable.setTypeName(variablesTypeName);
        callerVariable.setValue("test");

        caller.setVariables(List.of(callerVariable));

        callersBase.setCallers(List.of(caller));

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
