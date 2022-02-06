package com.example.telephony.service.charts.pie;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPartPieChartDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPieChartDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.enums.exception.messages.ChartExceptionMessages;
import com.example.telephony.exception.ChartException;
import com.example.telephony.service.DialingService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PieChart {
    private final DialingService dialingService;
    private final Map<DialCallerStatus, Integer> countResultsPerStatus;

    public PieChart(DialingService dialingService) {
        this.dialingService = dialingService;
        countResultsPerStatus = new HashMap<>();
    }

    public DialingResultPieChartDto createChart() {
        return createChart(null);
    }

    public DialingResultPieChartDto createChart(Dialing dialing) {
        fillCountResultsPerStatus();
        return createPieChartByDialing(dialing);
    }

    private void fillCountResultsPerStatus() {
        if (!countResultsPerStatus.isEmpty()) {
            throw new ChartException(ChartExceptionMessages.CHART_ALREADY_CREATED);
        }

        for (DialCallerStatus status : DialCallerStatus.values()) {
            countResultsPerStatus.put(status, 0);
        }
    }

    private DialingResultPieChartDto createPieChartByDialing(Dialing dialing) {
        int count = getCountDials(dialing);
        int countInProgress = putPercentCountAndGetCountInProgress(dialing, count);
        countResultsPerStatus.put(DialCallerStatus.IN_PROGRESS, calculatePercent(countInProgress, count));
        return buildResult(count);
    }

    private int putPercentCountAndGetCountInProgress(Dialing dialing, int count) {
        int countInProgress = count;
        for (DialCallerStatus status : DialCallerStatus.values()) {
            if (status == DialCallerStatus.IN_PROGRESS) {
                continue;
            }

            int statusCount = getCountDialsByStatus(dialing, status);
            countInProgress -= statusCount;
            countResultsPerStatus.put(status, statusCount);
        }

        return countInProgress;
    }

    private int getCountDials(Dialing dialing) {
        return dialing == null ?
                dialingService.getAllCountDialingsCallers() :
                dialingService.getCountDialingCallers(dialing);
    }

    private int getCountDialsByStatus(Dialing dialing, DialCallerStatus status) {
        return dialing == null ?
                dialingService.getAllCountDialsByStatus(status) :
                dialingService.getCountDialsByStatus(dialing, status);
    }

    private DialingResultPieChartDto buildResult(int count) {
        DialingResultPieChartDto result = new DialingResultPieChartDto();
        result.setCountCallers(count);
        result.setCountSuccess(countResultsPerStatus.get(DialCallerStatus.CORRECT));
        result.setPercentSuccess(calculatePercent(result.getCountSuccess(), result.getCountCallers()));

        result.setParts(countResultsPerStatus.keySet()
                .stream()
                .map(status -> buildPiePartChart(status, count))
                .collect(Collectors.toList()));

        return result;
    }

    private DialingResultPartPieChartDto buildPiePartChart(DialCallerStatus status, int count) {
        DialingResultPartPieChartDto result = new DialingResultPartPieChartDto();
        result.setKey(status.name());
        result.setName(status.getMessage());
        result.setValue(calculatePercent(countResultsPerStatus.get(status), count));
        return result;
    }

    private int calculatePercent(int statusCount, int count) {
        return (statusCount * 100) / count;
    }
}
