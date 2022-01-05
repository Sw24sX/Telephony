package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.service.DialingService;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DialingSuccessCallsMapper {
    @Autowired
    protected DialingService dialingService;

    public List<DialingResultSuccessCallsChartDto> fromDialing(Dialing dialing, int countSteps) {
        List<DialingCallerResult> results = dialingService.getSuccessCallersResultOrderByCreatedDate(dialing);
        if (CollectionUtils.isEmpty(results)) {
            return new ArrayList<>();
        }

        long startDate = results.get(0).getCreated().getTime();
        long endDate = results.get(results.size() - 1).getCreated().getTime();
        long step = (long) Math.ceil((endDate - startDate) / (double) countSteps);
        return calculateChart(results, createSteps(step, startDate, countSteps));
    }

    private List<DialingResultSuccessCallsChartDto> createSteps(long step, long startDate, int countStep) {
        List<DialingResultSuccessCallsChartDto> result = new ArrayList<>();
        for (int i = 1; i <= countStep; i++) {
            result.add(new DialingResultSuccessCallsChartDto(0, new Date(startDate + step * i)));
        }
        return result;
    }

    private List<DialingResultSuccessCallsChartDto> calculateChart(List<DialingCallerResult> callerResults, List<DialingResultSuccessCallsChartDto> steps) {
        if (CollectionUtils.isEmpty(steps) || CollectionUtils.isEmpty(callerResults)) {
            return steps;
        }

        int currentStepDateIndex = 0;
        int currentResultIndex = 0;
        while(currentResultIndex < callerResults.size()) {
            DialingResultSuccessCallsChartDto currentStep = steps.get(currentStepDateIndex);
            if (callerResults.get(currentResultIndex).getCreated().getTime() > currentStep.getDate().getTime()) {
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
}
