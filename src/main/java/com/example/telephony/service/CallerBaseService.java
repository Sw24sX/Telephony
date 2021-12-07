package com.example.telephony.service;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.service.file.CallersBaseParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CallerBaseService {
    private final CallerBaseRepository callerBaseRepository;

    public CallerBaseService(CallerBaseRepository callerBaseRepository) {
        this.callerBaseRepository = callerBaseRepository;
    }

    public List<CallersBase> getAll() {
        return callerBaseRepository.findAllByConfirmedIs(true);
    }

    public Page<CallersBase> getPage(int number, int size, FieldsPageSort fieldsPageSort,
                                     Sort.Direction direction, String name) {
        Sort sort = Sort.by(direction, fieldsPageSort.getFieldName());
        Pageable pageable = PageRequest.of(number, size, sort);
        return callerBaseRepository.findAllByConfirmedIs(true, "%" + name + "%", pageable);
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
    }

    public CallersBase uploadFromExelFile(MultipartFile multipartFile, String name) {
        CallersBaseParser callersBaseParser = new CallersBaseParser(getInputStream(multipartFile));
        CallersBase callersBase = callersBaseParser.parseExelToCallersBase();
        callersBase.setName(name);

        return callerBaseRepository.save(callersBase);
    }

    private InputStream getInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }
}
