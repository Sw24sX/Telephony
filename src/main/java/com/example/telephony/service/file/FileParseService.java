package com.example.telephony.service.file;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.enums.FileParsingExceptionMessage;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.exception.TelephonyException;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileParseService {
    public final static String NOT_VALID_VALUE = "NULL";
    private final static Set<String> PHONE_COLUMN_NAME =
            new HashSet<>(Lists.newArrayList("number", "phone number", "телефон", "номер телефона", "phone"));

    public CallersBase parseExelToCallersBase(MultipartFile multipartFile) {
        Workbook workbook = createWorkbook(multipartFile);
        int firstSheetNumber = 0;
        Sheet sheet = workbook.getSheetAt(firstSheetNumber);
        checkFormatCorrected(sheet);
        List<String> columns = getCorrectColumnsName(sheet);
        return getCallersBase(sheet, columns);
    }

    private Workbook createWorkbook(MultipartFile multipartFile) {
        try {
            return new XSSFWorkbook(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    private void checkFormatCorrected(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 2) {
            throw new FileParsingException(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage());
        }
    }

    private List<String> getCorrectColumnsName(Sheet sheet) {
        List<String> result = new ArrayList<>();
        for (Cell cell : sheet.getRow(sheet.getFirstRowNum())) {
            result.add(getColumnCellName(cell));
        }

        checkCorrectColumnNames(result);
        return result;
    }

    private String getColumnCellName(Cell cell) {
        if (cell.getCellType() != CellType.STRING) {
            throw new FileParsingException(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage());
        }
        String cellValue = cell.getStringCellValue().trim();
        if (!cellValueIsValid(cellValue)) {
            throw new FileParsingException(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage());
        }

        return cellValue;
    }

    private void checkCorrectColumnNames(List<String> columns) {
        if (columns.isEmpty()) {
            throw new FileParsingException(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage());
        }
    }

    private CallersBase getCallersBase(Sheet sheet, List<String> columns) {
        List<Caller> callers = getCallers(sheet, columns);
        CallersBase callersBase = new CallersBase();
        callersBase.setVariablesList(columns.toArray(new String[0]));
        callersBase.setCallers(callers);
        return callersBase;
    }

    private List<Caller> getCallers(Sheet sheet, List<String> columnsNames) {
        List<Caller> result = new ArrayList<>();

        String columnPhoneNumber = findUniqueColumnWithPhoneNumber(columnsNames);
        int startDataNumberRow = sheet.getFirstRowNum() + 1;

        for(int i = startDataNumberRow; i <= sheet.getLastRowNum(); i++) {
            result.add(createCaller(sheet.getRow(i), columnsNames, columnPhoneNumber));
        }

        return result;
    }

    private String findUniqueColumnWithPhoneNumber(List<String> columns) {
        String result = null;
        for(String name : columns) {
            if (PHONE_COLUMN_NAME.contains(name)) {
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

    private Caller createCaller(Row row, List<String> columnNames, String phoneNumberColumnName) {
        RowParseResult rowParseResult = readRow(row, columnNames);
        Map<String, String> variables = rowParseResult.getVariables();
        String number = variables.get(phoneNumberColumnName);
        Caller caller = new Caller();
        caller.setNumber(number);
        caller.setVariables(variables);
        caller.setValid(rowParseResult.isValid());
        return caller;
    }

    private RowParseResult readRow(Row row, List<String> columnsNames) {
        Map<String, String> variables = new HashMap<>();
        boolean isValid = true;
        int countCellsInRow = row.getLastCellNum();
        if(countCellsInRow > columnsNames.size()) {
            throw new FileParsingException(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage());
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            String cellValue = getValueCell(cell);
            variables.put(columnsNames.get(i), cellValue);
            if(!cellValueIsValid(cellValue)) {
                isValid = false;
            }
        }
        return new RowParseResult(variables, isValid);
    }

    private String getValueCell(Cell cell) {
        if (cell == null) {
            return NOT_VALID_VALUE;
        }

        switch (cell.getCellType()) {
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return NOT_VALID_VALUE;
        }
    }

    private boolean cellValueIsValid(String value) {
        return value != null && !value.isEmpty() && !value.equals(NOT_VALID_VALUE);
    }
}
