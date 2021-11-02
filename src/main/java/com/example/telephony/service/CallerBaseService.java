package com.example.telephony.service;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Sheet sheet = workbook.getSheetAt(0);
        List<String> columns = getColumnsName(sheet);
        List<Map<String, String>> data = getData(sheet, columns);
        int a = 1;
        return null;
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
        int firstRowNumber = sheet.getFirstRowNum();
        Row firstRow = sheet.getRow(firstRowNumber);
        for (Cell cell : firstRow) {
            if(cell.getCellType() == CellType._NONE) {
                break;
            }
            result.add(cell.getStringCellValue() .trim());
        }

        return result;
    }

    private List<Map<String, String>> getData(Sheet sheet, List<String> columnsNames) {
        List<Map<String, String>> result = new ArrayList<>();
        int startDataNumberRow = sheet.getFirstRowNum() + 1;
        for(int i = startDataNumberRow; i <= sheet.getLastRowNum(); i++) {
            result.add(readRow(sheet.getRow(i), columnsNames));
        }
        return result;
    }

    private Map<String, String> readRow(Row row, List<String> columnsNames) {
        Map<String, String> result = new HashMap<>();
        int maxNumberCell = row.getLastCellNum();
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            String cellValue = row.getCell(i).getStringCellValue();
            result.put(columnsNames.get(i), cellValue);
        }
        return result;
    }
}
