package com.example.telephony.enums;

public enum FileParsingExceptionMessage {
    PHONE_NUMBER_COLUMN_NOT_FOUND("Не удается найти столбец с номером телефона"),
    NOT_UNIQUE_PHONE_NUMBER_COLUMN("Не удается однозначно определить столбец с номером телефона"),
    FORMAT_NOT_CORRECT("Количество столбцов в заголовке таблицы не совпадает с количеством столбцов с данным"),
    EMPTY_FILE("Файл пуст"),
    EMPTY_DATA("Нет данных");

    private final String message;

    FileParsingExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
