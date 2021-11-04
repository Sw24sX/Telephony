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

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class FileParseService {
    public final static String NOT_VALID_VALUE = "NULL";
    private final static Set<String> PHONE_COLUMN_NAME =
            new HashSet<>(Lists.newArrayList("number", "phone number", "телефон", "номер телефона", "phone"));

    public CallersBase parseExelToCallersBase(InputStream inputStream) {
        Workbook workbook = createWorkbook(inputStream);
        int firstSheetNumber = 0;
        Sheet sheet = workbook.getSheetAt(firstSheetNumber);
        checkFormatCorrected(sheet);
        List<String> columns = getCorrectColumnsName(sheet);
        return getCallersBase(sheet, columns);
    }

    private Workbook createWorkbook(InputStream inputStream) {
        try {
            return new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    private void checkFormatCorrected(Sheet sheet) {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum - firstRowNum + 1 < 2) {
            throwNotCorrectFormat(FileParsingExceptionMessage.EMPTY_DATA.getMessage());
        }
    }

    private List<String> getCorrectColumnsName(Sheet sheet) {
        List<String> result = new ArrayList<>();
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        int firstCellNum = 0;
        int lastCellNum= firstRow.getLastCellNum();
        for (int i = firstCellNum; i < lastCellNum; i++) {
            Cell cell = firstRow.getCell(i);
            result.add(getColumnCellName(cell));
        }

        checkCorrectColumnNames(result);
        return result;
    }

    private String getColumnCellName(Cell cell) {
        if (cell == null) {
            throwNotCorrectFormat(FileParsingExceptionMessage.EMPTY_CELL_IN_HEADER.getMessage());
        }
        if (cell.getCellType() != CellType.STRING) {
            throwErrorInCell(cell);
        }
        String cellValue = cell.getStringCellValue().trim();
        if (cellValueIsInvalid(cellValue)) {
            throwErrorInCell(cell);
        }

        return cellValue;
    }

    private void checkCorrectColumnNames(List<String> columns) {
        if (columns.isEmpty()) {
            throwNotCorrectFormat(FileParsingExceptionMessage.EMPTY_LIST_COLUMNS_NAME.getMessage());
        }

        Set<String> columnsSet = new HashSet<>(columns);
        if(columnsSet.size() != columns.size()) {
            throwNotCorrectFormat(FileParsingExceptionMessage.HEADER_CONTAINS_NOT_UNIQUE_COLUMN.getMessage());
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
        int startDataNumberRow = 1;

        for(int i = startDataNumberRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null ) {
                result.add(createCaller(sheet.getRow(i), columnsNames, columnPhoneNumber));
            }
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
            throwErrorInCell(row.getCell(countCellsInRow - 1));
        }

        int firstCellNum = 0;
        for (int i = firstCellNum; i < columnsNames.size(); i++) {
            Cell cell = row.getCell(i);
            String cellValue = getValueCell(cell);
            variables.put(columnsNames.get(i), cellValue);
            if(cellValueIsInvalid(cellValue)) {
                isValid = false;
            }
        }
        return new RowParseResult(variables, isValid);
    }

    private String getValueCell(Cell cell) {
        if (cell == null) {
            return NOT_VALID_VALUE;
        }
        CellType type = cell.getCellType();
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

    private boolean cellValueIsInvalid(String value) {
        return value == null || value.isEmpty() || value.equals(NOT_VALID_VALUE);
    }

    private void throwNotCorrectFormat(String message) {
        throw new FileParsingException(
                String.format(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage(), message));
    }

    private void throwErrorInCell(Cell cell) {
        throwNotCorrectFormat(String.format(FileParsingExceptionMessage.ERROR_IN_CELL.getMessage(),
                cell.getRow().getRowNum(),
                cell.getColumnIndex()));
    }
}
