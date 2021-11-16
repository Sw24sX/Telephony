package com.example.telephony.service.file;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallerVariable;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.enums.FileParsingExceptionMessage;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.exception.FileParsingException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerBaseRepository;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.repository.CallerVariableRepository;
import com.example.telephony.repository.VariablesTypeNameRepository;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class CallersBaseParseService {
    public final static String NOT_VALID_VALUE = "INVALID";
    private final static Set<String> PHONE_COLUMN_NAME =
            new HashSet<>(Lists.newArrayList("number", "phone number", "телефон", "номер телефона", "phone"));

    private final CallerBaseRepository callerBaseRepository;
    private final CallerRepository callerRepository;
    private final VariablesTypeNameRepository variablesTypeNameRepository;
    private final CallerVariableRepository callerVariableRepository;

    public CallersBaseParseService(CallerBaseRepository callerBaseRepository, CallerRepository callerRepository,
                                   VariablesTypeNameRepository variablesTypeNameRepository,
                                   CallerVariableRepository callerVariableRepository) {
        this.callerBaseRepository = callerBaseRepository;
        this.callerRepository = callerRepository;
        this.variablesTypeNameRepository = variablesTypeNameRepository;
        this.callerVariableRepository = callerVariableRepository;
    }

    public CallersBase parseExelToCallersBase(InputStream inputStream, String name) {
        Workbook workbook = createWorkbook(inputStream);
        int firstSheetNumber = 0;
        Sheet sheet = workbook.getSheetAt(firstSheetNumber);
        checkFormatCorrected(sheet);
        CallersBase callersBase = createCallersBase(name);
        List<VariablesTypeName> columns = createColumnVariables(sheet, callersBase);
        callersBase.setVariablesList(columns);
        return saveCallersData(sheet, columns, callersBase);
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

    private CallersBase createCallersBase(String name) {
        CallersBase result = new CallersBase();
        result.setName(name);
        result.setConfirmed(false);
        return callerBaseRepository.save(result);
    }

    private List<VariablesTypeName> createColumnVariables(Sheet sheet, CallersBase callersBase) {
        List<VariablesTypeName> result = new ArrayList<>();
        Row firstRow = sheet.getRow(sheet.getFirstRowNum());
        Row secondRow = sheet.getRow(sheet.getFirstRowNum() + 1);

        int firstCellNum = 0;
        int lastCellNum= firstRow.getLastCellNum();
        for (int i = firstCellNum; i < lastCellNum; i++) {
            VariablesTypeName variablesTypeName = createVariablesTypeFromCell(i, firstRow, secondRow);
            variablesTypeName.setCallersBase(callersBase);
            result.add(variablesTypeName);
        }

        checkCorrectColumnNames(result);
        return variablesTypeNameRepository.saveAll(result);
    }

    private VariablesTypeName createVariablesTypeFromCell(int cellNum, Row firstRow, Row secondRow) {
        Cell firstRowCell = firstRow.getCell(cellNum);
        Cell secondRowCell = secondRow.getCell(cellNum);

        VariablesTypeName typeVariable = new VariablesTypeName();
        fillTypeVariable(firstRowCell, secondRowCell, typeVariable);
        return typeVariable;
    }

    private void fillTypeVariable(Cell firstRowCell, Cell secondRowCell, VariablesTypeName typeVariable) {
        if (firstRowCell == null) {
            //todo: delete callers base
            throwNotCorrectFormat(FileParsingExceptionMessage.EMPTY_CELL_IN_HEADER.getMessage());
        }

        if (secondRowCell == null) {
            //todo: delete callers base and correct exception
            throwNotCorrectFormat(String.format(FileParsingExceptionMessage.FORMAT_NOT_CORRECT.getMessage(), 2));
        }

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
                name = String.valueOf(firstRowCell.getNumericCellValue());
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

    private void checkCorrectColumnNames(List<VariablesTypeName> columns) {
        if (columns.isEmpty()) {
            throwNotCorrectFormat(FileParsingExceptionMessage.EMPTY_LIST_COLUMNS_NAME.getMessage());
        }

        Set<VariablesTypeName> columnsSet = new HashSet<>(columns);
        if(columnsSet.size() != columns.size()) {
            throwNotCorrectFormat(FileParsingExceptionMessage.HEADER_CONTAINS_NOT_UNIQUE_COLUMN.getMessage());
        }
    }

    private CallersBase saveCallersData(Sheet sheet, List<VariablesTypeName> columns, CallersBase callersBase) {
        List<Caller> callers = getCallers(sheet, columns, callersBase);
        callersBase.setCallers(callers);
        return callersBase;
    }

    private List<Caller> getCallers(Sheet sheet, List<VariablesTypeName> typeNames, CallersBase callersBase) {
        List<Caller> result = new ArrayList<>();

        int startDataNumberRow = 1;
        int phoneColumnIndex = findIndexColumnPhoneNumber(typeNames);

        for(int i = startDataNumberRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row != null ) {
                Caller caller = createCaller(sheet.getRow(i), typeNames, callersBase, phoneColumnIndex);
                result.add(callerRepository.save(caller));
            }
        }

        return result;
    }

    private int findIndexColumnPhoneNumber(List<VariablesTypeName> columns) {
        for (int i = 0; i < columns.size(); i++) {
            if (PHONE_COLUMN_NAME.contains(columns.get(i).getTableName())) {
                return i;
            }
        }

        throw new FileParsingException(FileParsingExceptionMessage.PHONE_NUMBER_COLUMN_NOT_FOUND.getMessage());
    }

    private Caller createCaller(Row row, List<VariablesTypeName> columnNames, CallersBase callersBase, int phoneColumnIndex) {
        Caller caller = new Caller();
        caller.setCallersBase(callersBase);
        caller = callerRepository.save(caller);
        return fillCaller(row, columnNames, caller, phoneColumnIndex);
    }

    private Caller fillCaller(Row row, List<VariablesTypeName> columnsNames, Caller caller, int phoneColumnIndex) {
        caller.setVariables(new ArrayList<>());
        for (int i = 0; i < columnsNames.size(); i++) {
            Cell cell = row.getCell(i);
            VariablesTypeName currentVariablesTypeName = columnsNames.get(i);
            String cellValue = getValueCell(cell);
            boolean isPhoneValue = i == phoneColumnIndex;
            CallerVariable callerVariable = createCallerVariable(cellValue, currentVariablesTypeName, isPhoneValue, caller);
            caller.getVariables().add(callerVariable);
        }

        return callerRepository.save(caller);
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
        return callerVariableRepository.save(callerVariable);
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
