package com.example.telephony.service.file;

import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.enums.exception.messages.FileParsingExceptionMessage;
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
        result.setCallers(new ArrayList<>());
        result.setVariablesList(new ArrayList<>());
        return result;
    }

    private Workbook createWorkbook(InputStream inputStream) {
        try {
            return new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new TelephonyException(e);
        }
    }

    public CallersBase parseExelToCallersBase() {
        checkFormatCorrected();
        createColumnVariables();
        CallersBaseDataParser dataParser = new CallersBaseDataParser(NOT_VALID_VALUE, PHONE_COLUMN_NAME, callersBase, sheet);
        dataParser.parseCallersData();
        callersBase.setCallers(dataParser.getCallersBase().getCallers());
        return callersBase;
    }

    private void checkFormatCorrected() {
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        int minRowsInFileWithCallersBase = 2;
        if (lastRowNum - firstRowNum + 1 < minRowsInFileWithCallersBase) {
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
        Cell firstRowCell = firstRow.getCell(cellNum);
        if (firstRowCell == null) {
            throw new FileParsingException(FileParsingExceptionMessage.EMPTY_CELL_IN_HEADER.getMessage());
        }

        Row secondRow = sheet.getRow(sheet.getFirstRowNum() + 1);
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
}
