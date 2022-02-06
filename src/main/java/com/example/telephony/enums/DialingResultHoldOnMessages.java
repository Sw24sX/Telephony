package com.example.telephony.enums;

public enum DialingResultHoldOnMessages {
    INCORRECT_NUMBER("Некорректный номер телефона"),
    COULD_NOT_GET_THROUGH("Не удалось дозвониться"),
    SCENARIO_HOLD_ON("Сброс звонка до завершения сценария"),
    INCORRECT_CALLER_VARIABLE("Некорректные пользовательские переменные");

    private final String message;

    DialingResultHoldOnMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
