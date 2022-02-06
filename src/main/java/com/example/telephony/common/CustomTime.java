package com.example.telephony.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomTime {
    private long hours;
    private long minutes;
    private long seconds;

    public static CustomTime fromMilliseconds(double milliseconds) {
        long millisecondsPerHour = 60 * 60 * 1000;
        long millisecondsPerMinutes = 60 * 1000;
        long millisecondsPerSeconds = 1000;

        return new CustomTime((int) milliseconds / millisecondsPerHour,
                (int) milliseconds % millisecondsPerHour / millisecondsPerMinutes,
                (int) milliseconds % millisecondsPerHour % millisecondsPerMinutes / millisecondsPerSeconds);
    }
}
