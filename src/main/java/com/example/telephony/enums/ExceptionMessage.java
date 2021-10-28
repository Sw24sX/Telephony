package com.example.telephony.enums;

public enum ExceptionMessage {
    SOUND_NOT_FOUND("Запись %s не найдена."),
    CALLER_NOT_FOUND("Абонент %s не найден"),
    SCENARIO_NOT_FOUND("Сценарий %s не найден"),
    PROPERTY_NOT_FOUND("Свойство \"%s\" не указано"),
    FILE_NAME_IS_NULL("Файл не содержит имени"),
    COULD_NOT_STORE_FILE("Не удалось сохранить файл %s"),
    NOT_INITIALIZE_FOLDER_FOR_UPLOAD("Не удалось создать папку для сохранения файлов");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
