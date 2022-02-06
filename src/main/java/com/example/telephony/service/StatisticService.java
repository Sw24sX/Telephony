package com.example.telephony.service;

import com.example.telephony.common.CustomTime;
import com.example.telephony.service.charts.pie.PieChart;
import com.example.telephony.service.charts.pointing.PointingChart;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.CommonStatisticDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPartPieChartDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPieChartDto;
import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.repository.DialingCallerResultRepository;
import com.example.telephony.repository.DialingStatisticRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
        return new PointingChart(dialingService.getAllStartCallTimeByDialing(dialing))
                .createChart()
                .collect(Collectors.toList());
    }

    public List<DialingResultSuccessCallsChartDto> createSuccessChartByAll() {
        return new PointingChart(dialingService.getAllStartCallTime())
                .createChart()
                .collect(Collectors.toList());
    }

    public DialingResultPieChartDto createPieChartAllStatistic() {
        return new PieChart(dialingService).createChart();
    }

    public DialingResultPieChartDto createPieChartByDialing(Dialing dialing) {
        return new PieChart(dialingService).createChart(dialing);
    }

    public CommonStatisticDto createCommonStatistic() {
        CommonStatisticDto result = new CommonStatisticDto();
        result.setAverageDialingsDuration(CustomTime.fromMilliseconds(dialingStatisticRepository.getAverageDialingDuration()));
        result.setTotalDialings(dialingStatisticRepository.getCountDialingsRun());
        result.setAverageNumberOfCallsPerDialing(dialingCallerResultRepository.getAverageCountCallers());
        Double suc = dialingCallerResultRepository.getAverageCallDuration();

        result.setAverageCallDuration(CustomTime.fromLocalTime(LocalTime.ofSecondOfDay(suc.intValue())));
        return  result;
    }
}
