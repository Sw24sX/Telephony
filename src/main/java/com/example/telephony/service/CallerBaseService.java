package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.enums.FileParsingExceptionMessage;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
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

    public CallersBase uploadFromExelFile(MultipartFile multipartFile, String name){
        Workbook workbook = createWorkbook(multipartFile);
        int firstSheetNumber = 0;
        Sheet sheet = workbook.getSheetAt(firstSheetNumber);
        List<String> columns = getColumnsName(sheet);

        List<Caller> callers = getCallers(sheet, columns);
        callers = callerRepository.saveAll(callers);

        CallersBase callersBase = new CallersBase();
        callersBase.setName(name);
        callersBase.setVariablesList(columns.toArray(new String[0]));
        callersBase.setCallers(callers);
        return callerBaseRepository.save(callersBase);
    }

    private Workbook createWorkbook(MultipartFile multipartFile) {
        try {
            return new XSSFWorkbook(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    private List<String> getColumnsName(Sheet sheet) {
        List<String> result = new ArrayList<>();
        for (Cell cell : sheet.getRow(sheet.getFirstRowNum())) {
            if(cell.getCellType() == CellType._NONE) {
                break;
            }
            result.add(cell.getStringCellValue() .trim());
        }

        return result;
    }

    private List<Caller> getCallers(Sheet sheet, List<String> columnsNames) {
        List<Caller> result = new ArrayList<>();

        String columnPhoneNumber = findColumnWithPhoneNumber(columnsNames);
        int startDataNumberRow = sheet.getFirstRowNum() + 1;

        for(int i = startDataNumberRow; i <= sheet.getLastRowNum(); i++) {
            result.add(createCaller(sheet.getRow(i), columnsNames, columnPhoneNumber));
        }

        return result;
    }

    private String findColumnWithPhoneNumber(List<String> columns) {
        String result = null;
        for(String name : columns) {
            if (columnIsPhoneNumber(name)) {
                if (result != null) {
                    throw new FileParsingException(FileParsingExceptionMessage.NOT_UNIQUE_PHONE_NUMBER_COLUMN.getMessage());
                }

                result = name;
            }
        }

        if (result == null) {
            throw new FileParsingException(FileParsingExceptionMessage.PHONE_NUMBER_COLUMN_NOT_FOUND.getMessage());
        }

        return result;
    }

    private boolean columnIsPhoneNumber(String name) {
        Set<String> phoneNumbersNameColumn =
                new HashSet<>(Lists.newArrayList("number", "phone number", "телефон", "номер телефона", "phone"));
        return phoneNumbersNameColumn.contains(name);
    }

    private Caller createCaller(Row row, List<String> columnNames, String phoneNumberColumnName) {
        Map<String, String> variables = readRow(row, columnNames);
        String number = variables.get(phoneNumberColumnName);
        checkCorrectPhoneNumber(number);
        Caller caller = new Caller();
        caller.setNumber(number);
        caller.setVariables(variables);
        return caller;
    }

    private void checkCorrectPhoneNumber(String number) {
        //TODO
    }

    private Map<String, String> readRow(Row row, List<String> columnsNames) {
        Map<String, String> result = new HashMap<>();
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            String cellValue = row.getCell(i).getStringCellValue();
            result.put(columnsNames.get(i), cellValue);
        }
        return result;
    }
}
