package com.example.telephony.service.file;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallerVariable;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.enums.FileParsingExceptionMessage;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.exception.TelephonyException;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CallersBaseParser {
    public final static String NOT_VALID_VALUE = "INVALID";
    private final static Set<String> PHONE_COLUMN_NAME =
            new HashSet<>(Lists.newArrayList("number", "phone number", "телефон", "номер телефона", "phone"));

    private final CallersBase callersBase;
    private final List<VariablesTypeName> columns;
    private final Sheet sheet;

    public CallersBaseParser(InputStream inputStream) {
        this.callersBase = createCallerBase();
        this.columns = this.callersBase.getVariablesList();
        int firstSheetNumber = 0;
        sheet = createWorkbook(inputStream).getSheetAt(firstSheetNumber);
    }

    private CallersBase createCallerBase() {
        CallersBase result = new CallersBase();
        result.setConfirmed(false);
        result.setCallers(new ArrayList<>());
        result.setVariablesList(new ArrayList<>());
        return result;
    }

    private Workbook createWorkbook(InputStream inputStream) {
        try {
            return new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    public CallersBase parseExelToCallersBase() {
        checkFormatCorrected();
        createColumnVariables();
        parseCallersData();
        return callersBase;
    }

    private void checkFormatCorrected() {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum - firstRowNum + 1 < 2) {
            throw new FileParsingException(FileParsingExceptionMessage.EMPTY_DATA.getMessage());
        }
    }

    private void createColumnVariables() {
        int firstCellNum = 0;
        int lastCellNum= sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();

        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
            columns.add(createVariablesTypeFromCell(cellNum));
        }

        checkCorrectColumnNames();
    }

    private VariablesTypeName createVariablesTypeFromCell(int cellNum) {
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        Row secondRow = sheet.getRow(sheet.getFirstRowNum() + 1);

        Cell firstRowCell = firstRow.getCell(cellNum);
        if (firstRowCell == null) {
            throw new FileParsingException(FileParsingExceptionMessage.EMPTY_CELL_IN_HEADER.getMessage());
        }

        Cell secondRowCell = secondRow.getCell(cellNum);
        if (secondRowCell == null) {
            throw new FileParsingException(String.format(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage(), 2));
        }

        VariablesTypeName typeVariable = new VariablesTypeName();
        typeVariable.setCallersBase(callersBase);
        fillTypeVariable(firstRowCell, secondRowCell, typeVariable);
        return typeVariable;
    }

    private void fillTypeVariable(Cell firstRowCell, Cell secondRowCell, VariablesTypeName typeVariable) {
        String name;
        switch (secondRowCell.getCellType()) {
            case BOOLEAN:
                typeVariable.setType(VariablesType.BOOLEAN);
                name = String.valueOf(firstRowCell.getBooleanCellValue());
                setNameInTypeVariable(typeVariable, name, firstRowCell);
                break;
            case STRING:
                typeVariable.setType(VariablesType.STRING);
                name = firstRowCell.getStringCellValue().trim();
                setNameInTypeVariable(typeVariable, name, firstRowCell);
                break;
            case NUMERIC:
                typeVariable.setType(VariablesType.NUMBER);
                name = String.valueOf(firstRowCell.getStringCellValue());
                setNameInTypeVariable(typeVariable, name, firstRowCell);
                break;
            default:
                typeVariable.setType(VariablesType.INDEFINITE);
                setNameInTypeVariable(typeVariable, NOT_VALID_VALUE, firstRowCell);
                break;
        }
    }

    private void setNameInTypeVariable(VariablesTypeName typeVariable, String name, Cell firstRowCell) {
        if (cellValueIsInvalid(name)) {
            throwErrorInCell(firstRowCell);
        }
        typeVariable.setTableName(name);
        typeVariable.setCurrentName(name);
    }

    private boolean cellValueIsInvalid(String value) {
        return value == null || value.isEmpty() || value.equals(NOT_VALID_VALUE);
    }

    private void throwErrorInCell(Cell cell) {
        throw new FileParsingException(String.format(FileParsingExceptionMessage.ERROR_IN_CELL.getMessage(),
                cell.getRow().getRowNum(),
                cell.getColumnIndex()));
    }

    private void checkCorrectColumnNames() {
        if (columns.isEmpty()) {
            throw new FileParsingException(FileParsingExceptionMessage.EMPTY_LIST_COLUMNS_NAME.getMessage());
        }

        Set<String> columnsSet = columns.stream().map(VariablesTypeName::getTableName).collect(Collectors.toSet());
        if(columnsSet.size() != columns.size()) {
            throw new FileParsingException(FileParsingExceptionMessage.HEADER_CONTAINS_NOT_UNIQUE_COLUMN.getMessage());
        }
    }

    private void parseCallersData() {
        int startDataNumberRow = 1;
        int phoneColumnIndex = findIndexColumnPhoneNumber();

        for(int i = startDataNumberRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null ) {
                Caller caller = createCaller(sheet.getRow(i), phoneColumnIndex);
                callersBase.getCallers().add(caller);
            }
        }
    }

    private int findIndexColumnPhoneNumber() {
        for (int i = 0; i < columns.size(); i++) {
            if (PHONE_COLUMN_NAME.contains(columns.get(i).getTableName())) {
                return i;
            }
        }

        throw new FileParsingException(FileParsingExceptionMessage.PHONE_NUMBER_COLUMN_NOT_FOUND.getMessage());
    }

    private Caller createCaller(Row row, int phoneColumnIndex) {
        Caller caller = new Caller();
        caller.setCallersBase(callersBase);
        caller.setVariables(new ArrayList<>());
        caller.setNumber(row.getRowNum());
        caller.setValid(true);

        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.getCell(i);
            VariablesTypeName currentVariablesTypeName = columns.get(i);
            String cellValue = getValueCell(cell);
            boolean isPhoneValue = i == phoneColumnIndex;
            CallerVariable callerVariable = createCallerVariable(cellValue, currentVariablesTypeName, isPhoneValue, caller);
            caller.getVariables().add(callerVariable);
            caller.setValid(caller.isValid() && callerVariable.isValid());
        }

        return caller;
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

    private CallerVariable createCallerVariable(String value, VariablesTypeName variablesTypeName, boolean isPhoneValue, Caller caller) {
        CallerVariable callerVariable = new CallerVariable();
        callerVariable.setValue(value);
        callerVariable.setTypeName(variablesTypeName);
        callerVariable.setValid(true);
        callerVariable.setPhoneColumn(isPhoneValue);
        callerVariable.setCaller(caller);
        if(cellValueIsInvalid(value) || variablesTypeName.getType() == VariablesType.INDEFINITE) {
            callerVariable.setValid(false);
        }

        return callerVariable;
    }
}
