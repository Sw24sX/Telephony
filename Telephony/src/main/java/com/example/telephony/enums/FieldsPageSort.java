package com.example.telephony.enums;

public enum FieldsPageSort {
    NAME("Имя", "name"),
    CREATION_DATE("Дата создания", "created");

    private final String name;
    private final String fieldName;

    FieldsPageSort(String name, String fieldName) {
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
