package com.example.telephony.service;

import com.example.telephony.common.CustomTime;
import com.example.telephony.common.SuccessChartSteps;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.dto.CommonStatisticDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPartPieChartDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPieChartDto;
import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.DialingCallerResultRepository;
import com.example.telephony.repository.DialingStatisticRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class StatisticService {
    private static final String IN_PROGRESS = "В процессе";

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
        return createSuccessChart(dialingService.getSuccessCallersResultOrderByCreatedDateByDialing(dialing));
    }

    public List<DialingResultSuccessCallsChartDto> createSuccessChartByAll() {
        return createSuccessChart(dialingService.getSuccessCallersResultOrderByCreatedDate());
    }

    private List<DialingResultSuccessCallsChartDto> createSuccessChart(List<DialingCallerResult> results) {
        if (CollectionUtils.isEmpty(results)) {
            return new ArrayList<>();
        }

        long startDate = getMillsFromStartDay(results.get(0).getCreated());
        long endDate = getMillsFromStartDay(results.get(results.size() - 1).getCreated());
        long deltaTime = endDate - startDate;
        long step = SuccessChartSteps.getStep(deltaTime);
        return calculateChart(results, createSteps(step, results.get(0).getCreated().getTime()));
    }

    private List<DialingResultSuccessCallsChartDto> createSteps(long step, long startDate) {
        List<DialingResultSuccessCallsChartDto> result = new ArrayList<>();
        for (int i = 0; i < SuccessChartSteps.getStepCount(step); i++) {
            Date date = new Date(startDate + step * i);
            result.add(new DialingResultSuccessCallsChartDto(0, date, getTime(date)));
        }
        result.sort(Comparator.comparingLong(a -> getMillsFromStartDay(a.getDate())));
        return result;
    }

    private String getTime(Date date) {
        //todo not correct
        String timePattern = "%s:%s";
        String minutes = String.valueOf(date.getMinutes());
        return String.format(timePattern, date.getHours(), minutes.length() > 1 ? minutes : '0' + minutes);
    }

    private List<DialingResultSuccessCallsChartDto> calculateChart(List<DialingCallerResult> callerResults, List<DialingResultSuccessCallsChartDto> steps) {
        if (CollectionUtils.isEmpty(steps) || CollectionUtils.isEmpty(callerResults)) {
            return steps;
        }

        int currentStepDateIndex = 0;
        int currentResultIndex = 0;
        while(currentResultIndex < callerResults.size()) {
            DialingResultSuccessCallsChartDto currentStep = steps.get(currentStepDateIndex);
            long currentMills = getMillsFromStartDay(callerResults.get(currentResultIndex).getCreated());
            long stepMills = getMillsFromStartDay(currentStep.getDate());
            if (currentMills > stepMills) {
                if (currentStepDateIndex + 1 >= steps.size()) {
                    //todo correct message
                    throw new TelephonyException("Incorrect steps for chart");
                }

                currentStepDateIndex += 1;
                continue;
            }

            currentStep.setSuccessCalls(currentStep.getSuccessCalls() + 1);
            currentResultIndex++;
        }

        return steps;
    }

    private long getMillsFromStartDay(Date date) {
        return date.getHours() * 60 * 60 * 1000 +
                date.getMinutes() * 60 * 1000 +
                date.getSeconds() * 1000;
    }

    public DialingResultPieChartDto createPieChartByDialing(Dialing dialing) {
        int count = dialingService.getCountDialingCallers(dialing);
        int countInProgress = count;
        int countSuccess = 0;
        List<DialingResultPartPieChartDto> parts = new ArrayList<>();
        for (DialCallerStatus status : DialCallerStatus.values()) {
            int percentCount = dialingService.getCountDialsByStatus(dialing, status);
            countInProgress -= percentCount;

            int percent = calculatePercent(percentCount, count);
            parts.add(new DialingResultPartPieChartDto(status.getMessage(), percent));

            if (status == DialCallerStatus.CORRECT) {
                countSuccess = percentCount;
            }
        }
        parts.add(new DialingResultPartPieChartDto(IN_PROGRESS, calculatePercent(countInProgress, count)));

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
