package com.example.telephony.enums;

public enum ScenarioExceptionMessages {
    NO_ROOT_NODE("Стартовая вершина не найдена"),
    ROOT_NODE_HAVE_NOT_CHILD("Стартовая вершина не имеет потомков"),
    NODE_NOT_FOUND("Вершина %s не найдена"),
    SOURCE_HANDLER_NOT_FOUND("Для вершины %s не найдена цифра %s");

    private final String message;

    ScenarioExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
