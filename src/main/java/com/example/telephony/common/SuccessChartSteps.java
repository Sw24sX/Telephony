package com.example.telephony.common;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuccessChartSteps {
    private static Long MAX_STEP = (long) 3600000;
    private static Integer MAX_STEP_COUNT = 24;

    private static List<Long> deltas = Arrays.asList((long) 600000, (long) 3600000, (long) 43200000);

    private static Map<Long, Long> deltaSteps = new HashMap<>() {{
        put((long) 600000, (long) 60000);
        put((long) 3600000, (long) 300000);
        put((long) 43200000, (long) 1800000);
    }};

    private static Map<Long, Integer> deltaStepBySteps = new HashMap<>() {{
        put((long) 60000, 10);
        put((long) 300000, 12);
        put((long) 1800000, 24);
    }};

    public static Integer getStepCount(long step) {
        return deltaStepBySteps.containsKey(step) ? deltaStepBySteps.get(step) : MAX_STEP_COUNT;
    }

    public static Long getStep(long delta) {
        for(Long step : SuccessChartSteps.deltas) {
            if (delta < step) {
                return SuccessChartSteps.deltaSteps.get(step);
            }
        }

        return SuccessChartSteps.MAX_STEP;
    }
}
