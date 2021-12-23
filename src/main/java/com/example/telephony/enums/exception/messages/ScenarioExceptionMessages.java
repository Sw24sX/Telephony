package com.example.telephony.enums.exception.messages;

public enum ScenarioExceptionMessages {
    NO_ROOT_NODE("Стартовая вершина не найдена"),
    ROOT_NODE_HAVE_NOT_CHILD("Стартовая вершина не имеет потомков"),
    NODE_NOT_FOUND("Вершина %s не найдена"),
    SOURCE_HANDLER_NOT_FOUND("Для ребра %s не найдена цифра %s"),
    STEP_AFTER_END("После шага 'End' не может быть других шагов"),
    AFTER_START_STEP_MAY_BE_ONLY_ONE_STEP("После шага 'Start' может быть только один вариант ответа"),
    ANSWER_BUTTON_ALREADY_EXIST("Кнопка %s уже имеется в списке ответов"),
    START_SCENARIO_TYPE_CAN_BE_ONLY_ONE("Только одна вершина может иметь тип 'Start'"),
    CALLER_HAVE_NOT_VARIABLE("Абонент не содержит переменную %s");

    private final String message;

    ScenarioExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
