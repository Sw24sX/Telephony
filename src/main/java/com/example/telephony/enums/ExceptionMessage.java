package com.example.telephony.enums;

public enum ExceptionMessage {
    SOUND_NOT_FOUND("Запись %s не найдена."),
    CALLER_NOT_FOUND("Абонент %s не найден"),
    SCENARIO_NOT_FOUND("Сценарий %s не найден"),
    PROPERTY_NOT_FOUND("Свойство \"%s\" не указано"),
    FILE_NAME_IS_NULL("Файл не содержит имени"),
    COULD_NOT_STORE_FILE("Не удалось сохранить файл %s"),
    NOT_INITIALIZE_FOLDER_FOR_UPLOAD("Не удалось создать папку для сохранения файлов"),
    SCENARIO_MANAGER_NOT_FOUND_CHANNEL_ID("Менеджер сценариев не обнаружил уникальный номер %s"),
    CALLERS_ALREADY_CREATED("Абоненты уже находятся в базе данных"),
    CALLERS_NOT_CREATED("Абоненты не записаны в базу данных"),
    CALLERS_BASE_NOT_CONTAINS_CALLER("База абонентов %s не содержит абонента %s"),
    CALLERS_BASE_NOT_FOUND("База клиентов %s не найдена"),
    GENERATED_FILE_NOT_FOUND("Сгенерированный файл %s не найден"),
    WRONG_ENUM_CODE("Код перечисления %s не найден");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
