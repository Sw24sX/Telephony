package com.example.telephony.service;

import com.example.telephony.common.CustomTime;
import com.example.telephony.service.charts.PointingChart.PointingChart;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.CommonStatisticDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPartPieChartDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPieChartDto;
import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.repository.DialingCallerResultRepository;
import com.example.telephony.repository.DialingStatisticRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticService {
    private final DialingService dialingService;
    private final DialingStatisticRepository dialingStatisticRepository;
    private final DialingCallerResultRepository dialingCallerResultRepository;

    public StatisticService(DialingService dialingService, DialingStatisticRepository dialingStatisticRepository,
                            DialingCallerResultRepository dialingCallerResultRepository) {
        this.dialingService = dialingService;
        this.dialingStatisticRepository = dialingStatisticRepository;
        this.dialingCallerResultRepository = dialingCallerResultRepository;
    }

    public List<DialingResultSuccessCallsChartDto> createSuccessChartByDialing(Long dialingId) {
        Dialing dialing = dialingService.getById(dialingId);
        return new PointingChart(dialingService.getSuccessCallersResultOrderByCreatedDateByDialing(dialing))
                .createChart()
                .collect(Collectors.toList());
    }

    public List<DialingResultSuccessCallsChartDto> createSuccessChartByAll() {
        return new PointingChart(dialingService.getSuccessCallersResultOrderByCreatedDate())
                .createChart()
                .collect(Collectors.toList());
    }

    public DialingResultPieChartDto createPieChartAllStatistic() {
        int count = dialingService.getAllCountDialingsCallers();
        int countInProgress = count;
        int countSuccess = 0;
        List<DialingResultPartPieChartDto> parts = new ArrayList<>();
        for (DialCallerStatus status : DialCallerStatus.values()) {
            if (status == DialCallerStatus.IN_PROGRESS) {
                continue;
            }

            int percentCount = dialingService.getAllCountDialsByStatus(status);
            countInProgress -= percentCount;

            int percent = calculatePercent(percentCount, count);
            parts.add(new DialingResultPartPieChartDto(status.getMessage(), percent, status.name()));

            if (status == DialCallerStatus.CORRECT) {
                countSuccess = percentCount;
            }
        }
        parts.add(new DialingResultPartPieChartDto(DialCallerStatus.IN_PROGRESS.getMessage(), calculatePercent(countInProgress, count), DialCallerStatus.IN_PROGRESS.name()));

        DialingResultPieChartDto result = new DialingResultPieChartDto();
        result.setParts(parts);
        result.setCountCallers(count);
        result.setCountSuccess(countSuccess);
        result.setPercentSuccess(calculatePercent(result.getCountSuccess(), result.getCountCallers()));
        return result;
    }

    public DialingResultPieChartDto createPieChartByDialing(Dialing dialing) {
        int count = dialingService.getCountDialingCallers(dialing);
        int countInProgress = count;
        int countSuccess = 0;
        List<DialingResultPartPieChartDto> parts = new ArrayList<>();
        for (DialCallerStatus status : DialCallerStatus.values()) {
            if (status == DialCallerStatus.IN_PROGRESS) {
                continue;
            }

            int percentCount = dialingService.getCountDialsByStatus(dialing, status);
            countInProgress -= percentCount;

            int percent = calculatePercent(percentCount, count);
            parts.add(new DialingResultPartPieChartDto(status.getMessage(), percent, status.name()));

            if (status == DialCallerStatus.CORRECT) {
                countSuccess = percentCount;
            }
        }
        parts.add(new DialingResultPartPieChartDto(DialCallerStatus.IN_PROGRESS.getMessage(), calculatePercent(countInProgress, count), DialCallerStatus.IN_PROGRESS.name()));

        DialingResultPieChartDto result = new DialingResultPieChartDto();
        result.setParts(parts);
        result.setCountCallers(count);
        result.setCountSuccess(countSuccess);
        result.setPercentSuccess(calculatePercent(result.getCountSuccess(), result.getCountCallers()));
        return result;
    }

    private int calculatePercent(int percentCount, int count) {
        return (percentCount * 100) / count;
    }

    public CommonStatisticDto createCommonStatistic() {
        CommonStatisticDto result = new CommonStatisticDto();
        result.setAverageDialingsDuration(CustomTime.fromMilliseconds(dialingStatisticRepository.getAverageDialingDuration()));
        result.setTotalDialings(dialingStatisticRepository.getCountDialingsRun());
        result.setAverageNumberOfCallsPerDialing(dialingCallerResultRepository.getAverageCountCallers());
        Double suc = dialingCallerResultRepository.getAverageCallDuration();
        result.setAverageCallDuration(CustomTime.fromMilliseconds(suc));
        return  result;
    }
}
