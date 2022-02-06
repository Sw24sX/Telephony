package com.example.telephony.common;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.callers.base.CallersBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallersBaseBuilder {
    private List<String> columnNames;
    private String phoneColumnName;
    private List<Caller> callers;

    public CallersBaseBuilder(List<String> columnNames) {
        this.columnNames = columnNames;
        this.callers = new ArrayList<>();
    }

    public CallersBaseBuilder addPhoneColumnName(String phoneColumnName) {
        this.phoneColumnName = phoneColumnName;
        return this;
    }

    public CallersBaseBuilder addCallerRow(List<String> data, boolean isValid) {
        Map<String, String> variables = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            variables.put(columnNames.get(i), data.get(i));
        }
        Caller caller = new Caller();
//        caller.setNumber(variables.get(phoneColumnName));
//        caller.setVariables(variables);
//        caller.setValid(isValid);
        callers.add(caller);
        return this;
    }

    public CallersBase build() {
        CallersBase result = new CallersBase();
//        result.setVariablesList(columnNames.toArray(new String[0]));
        result.setCallers(callers);
        return result;
    }
}
