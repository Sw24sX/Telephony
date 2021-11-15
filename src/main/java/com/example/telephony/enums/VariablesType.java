package com.example.telephony.enums;

public enum VariablesType {
    STRING("Строка"),
    NUMBER("Число"),
    DATA("Дата"),
    TIME("Время"),
    BOOLEAN("Логический");

    private final String description;

    VariablesType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
