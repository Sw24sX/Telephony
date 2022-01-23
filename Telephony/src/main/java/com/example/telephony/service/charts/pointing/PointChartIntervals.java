package com.example.telephony.service.charts.pointing;

import com.example.telephony.enums.exception.messages.ChartExceptionMessages;
import com.example.telephony.exception.ChartException;

import java.util.*;

public class PointChartIntervals {
    public static final int MAX_INTERVAL = 86400;

    private static final Map<Integer, IntervalAndSteps> intervalsPerSeconds = new LinkedHashMap<>() {{
        put(600, new IntervalAndSteps(60, 10));
        put(3600, new IntervalAndSteps(300, 12));
        put(43200, new IntervalAndSteps(1800, 24));
        put(MAX_INTERVAL, new IntervalAndSteps(3600, 24));
    }};

    public static IntervalAndSteps getStep(int deltaOfSeconds) {
        int overDelta = deltaOfSeconds % MAX_INTERVAL == 0 ? MAX_INTERVAL : deltaOfSeconds % MAX_INTERVAL;

        for(int step : PointChartIntervals.intervalsPerSeconds.keySet()) {
            if (overDelta <= step) {
                return PointChartIntervals.intervalsPerSeconds.get(step);
            }
        }

        throw new ChartException(ChartExceptionMessages.INTERVAL_NOT_FOUND);
    }
}
