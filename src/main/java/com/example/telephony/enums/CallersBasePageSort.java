package com.example.telephony.enums;

public enum CallersBasePageSort {
    NAME("Имя", "name"),
    CREATION_DATE("Дата создания", "created");

    private final String name;
    private final String fieldName;

    CallersBasePageSort(String name, String fieldName) {
        this.name = name;
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public String getFieldName() {
        return fieldName;
    }
}
