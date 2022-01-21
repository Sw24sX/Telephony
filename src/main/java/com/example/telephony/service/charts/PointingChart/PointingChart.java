package com.example.telephony.service.charts.PointingChart;

import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.enums.exception.messages.ChartExceptionMessages;
import com.example.telephony.exception.ChartException;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

public class PointingChart {
    private final List<LocalTime> times;
    private final SortedMap<LocalTime, Integer> groupingTimes;

    public PointingChart(List<LocalTime> times) {
        this.times = times;
        groupingTimes = new TreeMap<>();
    }

    public Stream<DialingResultSuccessCallsChartDto> createChart() {
        IntervalAndSteps intervalAndSteps = PointChartIntervals.getStep(getSecondsDelta());
        int firstIndex = 0;
        fillGroupingTimes(times.get(firstIndex), intervalAndSteps.getIntervalInSeconds(), intervalAndSteps.getCountSteps());
        groupTimes(intervalAndSteps.getIntervalInSeconds());
        return mapGroupingTimes();
    }

    private int getSecondsDelta() {
        if (CollectionUtils.isEmpty(times)) {
            return 0;
        }

        int firstIndex = 0;
        int startSecondsOfDay = times.get(firstIndex).toSecondOfDay();
        int endSecondsOfDay = times.get(times.size() - 1).toSecondOfDay();
        return endSecondsOfDay - startSecondsOfDay;
    }

    private void fillGroupingTimes(LocalTime startTime, int interval, int count) {
        int startSecondsOfDay = startTime.toSecondOfDay();
        for (int time = startSecondsOfDay; time < startSecondsOfDay + count * interval; time += interval) {
            LocalTime ceilTime = ceilTime(LocalTime.ofSecondOfDay(time % PointChartIntervals.MAX_INTERVAL), interval);
            groupingTimes.put(ceilTime, 0);
        }
    }

    private void groupTimes(int interval) {
        for (LocalTime time : times) {
            LocalTime ceilTime = ceilTime(time, interval);
            if (!groupingTimes.containsKey(ceilTime)) {
                throw new ChartException(ChartExceptionMessages.NOT_CORRECT_GROUPING_TIME);
            }

            groupingTimes.put(ceilTime, groupingTimes.get(ceilTime) + 1);
        }
    }

    private LocalTime ceilTime(LocalTime time, int interval) {
        int secondsOfDay = time.toSecondOfDay();
        int overSeconds = secondsOfDay % interval;
        return LocalTime.ofSecondOfDay(secondsOfDay - overSeconds);
    }

    private Stream<DialingResultSuccessCallsChartDto> mapGroupingTimes() {
        return groupingTimes.keySet().stream().map(time -> {
            DialingResultSuccessCallsChartDto result = new DialingResultSuccessCallsChartDto();
            result.setStartTime(time);
            result.setSuccessCalls(groupingTimes.get(time));
            result.setStringTime(time.toString());
            return result;
        });
    }
}
