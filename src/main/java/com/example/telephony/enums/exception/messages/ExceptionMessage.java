package com.example.telephony.enums.exception.messages;

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
    WRONG_ENUM_CODE("Код перечисления %s не найден"),
    DIALING_NOT_FOUND("Обзванивание %s не найдено"),
    CAN_NOT_CHANGE_DIALING("Нельзя изменить запущенный или завершенный обзвон"),
    CAN_NOT_CREATE_DONE_DIALING("Нельзя создать выполненный обзвон"),
    DIALING_DATE_NOT_VALID("Дата обзвона не указана или указана неверно"),
    DIALING_ALREADY_ADDED_TO_DIALING_MANAGER("Обзваниевание уже добавлено в менеджер обзвонов"),
    DIALING_NOT_ADDED_TO_DIALING_MANAGER("Обзвон не добавлен в менеджер обзвонов"),
    DIALING_NOT_SCHEDULED_STATUS("Обзвон не является отложенным, его нельзя запустить");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
