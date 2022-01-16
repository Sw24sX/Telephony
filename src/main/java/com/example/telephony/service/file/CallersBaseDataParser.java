package com.example.telephony.service.file;

import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.callers.base.CallerVariable;
import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.enums.exception.messages.FileParsingExceptionMessage;
import com.example.telephony.exception.FileParsingException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CallersBaseDataParser {
    public final String NOT_VALID_VALUE;
    private final Set<String> PHONE_COLUMN_NAME;
    private final CallersBase callersBase;
    private final List<VariablesTypeName> columns;
    private final Sheet sheet;

    public CallersBaseDataParser(String not_valid_value, Set<String> phone_column_name, CallersBase callersBase, Sheet sheet) {
        NOT_VALID_VALUE = not_valid_value;
        PHONE_COLUMN_NAME = phone_column_name;
        this.callersBase = callersBase;
        this.columns = this.callersBase.getVariablesList();
        this.sheet = sheet;
    }

    public CallersBase getCallersBase() {
        return callersBase;
    }

    public void parseCallersData() {
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

    private boolean cellValueIsInvalid(String value) {
        return value == null || value.isEmpty() || value.equals(NOT_VALID_VALUE);
    }
}
