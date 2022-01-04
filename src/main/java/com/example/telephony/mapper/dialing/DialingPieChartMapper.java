package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.dialing.pie.chart.DialingResultPartPieChartDto;
import com.example.telephony.dto.dialing.pie.chart.DialingResultPieChartDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.service.DialingService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DialingPieChartMapper {
    private static final String IN_PROGRESS = "В процессе";

    @Autowired
    protected DialingService dialingService;

    public DialingResultPieChartDto fromDialing(Dialing dialing) {
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
}
