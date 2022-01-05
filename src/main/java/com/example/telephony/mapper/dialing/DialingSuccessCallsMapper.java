package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.service.DialingService;
import lombok.Data;
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

        countSteps = Math.min(countSteps, results.size());
        Date startDate = results.get(0).getCreated();
        Date endDate = results.get(results.size() - 1).getCreated();
        long step = (endDate.getTime() - startDate.getTime()) / countSteps;
        return calculateChart(results, step, new Date(startDate.getTime() + step));
    }

    private List<DialingResultSuccessCallsChartDto> calculateChart(List<DialingCallerResult> callerResults, long step, Date firstStep) {
        Date currentStepDate = new Date(firstStep.getTime());

        List<DialingResultSuccessCallsChartDto> result = new ArrayList<>();
        result.add(new DialingResultSuccessCallsChartDto(0, currentStepDate));

        for (DialingCallerResult dialingResult : callerResults) {
            long currentTime = currentStepDate.getTime();
            long resultTime = dialingResult.getCreated().getTime();
            if (dialingResult.getCreated().getTime() > currentStepDate.getTime()) {
                currentStepDate = new Date(currentStepDate.getTime() + step);
                result.add(new DialingResultSuccessCallsChartDto(0, currentStepDate));
            }

            DialingResultSuccessCallsChartDto point = result.get(result.size() - 1);
            point.setSuccessCalls(point.getSuccessCalls() + 1);
        }

        return result;
    }
}
