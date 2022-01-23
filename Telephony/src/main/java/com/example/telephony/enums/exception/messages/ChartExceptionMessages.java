package com.example.telephony.enums.exception.messages;

public enum ChartExceptionMessages {
    NOT_CORRECT_GROUPING_TIME("Время для группировки округлено неверно или группировка создана некорректно"),
    INTERVAL_NOT_FOUND("Не удалось определить интервал"),
    CHART_ALREADY_CREATED("График уже создан");

    private final String message;

    ChartExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
