package com.example.telephony.enums;

public enum ExceptionMessage {
    SOUND_NOT_FOUND("Запись %s не найдена."),
    CALLER_NOT_FOUND("Абонент %s не найден"),
    SCENARIO_NOT_FOUND("Сценарий %s не найден"),
    PROPERTY_NOT_FOUND("Свойство \"%s\" не указано"),
    FILE_NAME_IS_NULL("Файл не содержит имени"),
    GENERATED_FOLDER_MUST_NOT_CONTAINS_FOLDER("Директория 'generated' не должна содержать поддиректории"),
    FILE_NOT_FOUND("Файл %s не найден"),
    COULD_NOT_STORE_FILE("Не удалось сохранить файл %s"),
    NOT_INITIALIZE_FOLDER_FOR_UPLOAD("Не удалось создать папку для сохранения файлов"),
    SCENARIO_MANAGER_NOT_FOUND_ID("Менеджер сценариев не обнаружил уникальный номер %s"),
    SCENARIO_WAS_ALREADY_STARTED("Сценарий для канала %s уже был начат"),
    SCENARIO_NO_MORE_STEPS("Сценарий больше нет шагов"),
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
