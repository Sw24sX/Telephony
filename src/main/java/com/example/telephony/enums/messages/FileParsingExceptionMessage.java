package com.example.telephony.enums.messages;

public enum FileParsingExceptionMessage {
    PHONE_NUMBER_COLUMN_NOT_FOUND("Не удается найти столбец с номером телефона"),
    NOT_UNIQUE_PHONE_NUMBER_COLUMN("Не удается однозначно определить столбец с номером телефона"),
    FORMAT_NOT_CORRECT("Формат файла неверный. %s"),
    EMPTY_LIST_COLUMNS_NAME("Список заголовков таблицы пуст"),
    ERROR_IN_CELL("Ошибка в строке %s и столбце %s"),
    EMPTY_DATA("Нет данных"),
    EMPTY_CELL_IN_HEADER("Пустая ячейка в заголовке таблицы"),
    HEADER_CONTAINS_NOT_UNIQUE_COLUMN("В заголовке содержится неуникальный столбец");

    private final String message;

    FileParsingExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
