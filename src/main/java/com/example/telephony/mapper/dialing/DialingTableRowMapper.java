package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.dto.CallerVariablesDto;
import com.example.telephony.dto.dialing.table.DialingResultTableRowDto;
import com.example.telephony.enums.DialCallerStatus;
import com.example.telephony.mapper.CallerMapper;
import com.example.telephony.service.DialingService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DialingTableRowMapper {
    @Autowired
    protected DialingService dialingService;

    @Autowired
    protected CallerMapper callerMapper;

    public DialingResultTableRowDto fromCaller(Caller caller, Dialing dialing) {
        if (caller == null) {
            return null;
        }

        DialingResultTableRowDto result = new DialingResultTableRowDto();

        CallerDto callerDto = callerMapper.fromCaller(caller);
        result.setNumber(callerDto.getNumber());
        result.setOriginal(callerDto.getVariables());
        result.setExtra(createExtraVariables(dialingService.getDialResultByDialingAndCaller(dialing, caller).orElse(null)));
        return result;
    }

    private List<CallerVariablesDto> createExtraVariables(DialingCallerResult dialingCallerResult) {
        CallerVariablesDto status = new CallerVariablesDto();
        CallerVariablesDto answers = new CallerVariablesDto();

        if (dialingCallerResult == null) {
            status.setValue(DialCallerStatus.IN_PROGRESS.getMessage());
        } else {
            status.setValue(dialingCallerResult.getStatus().getMessage());
            status.setValid(true);

            answers.setValue(StringUtils.join(dialingCallerResult.getAnswers(), ";"));
            answers.setValid(true);
        }

        return Arrays.asList(status, answers);
    }
}
