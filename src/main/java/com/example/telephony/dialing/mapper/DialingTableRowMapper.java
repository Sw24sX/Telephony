package com.example.telephony.dialing.mapper;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.dialing.persistance.model.Dialing;
import com.example.telephony.dialing.persistance.model.DialingCallerResult;
import com.example.telephony.dto.caller.base.CallerDto;
import com.example.telephony.dialing.dto.table.DialingResultTableRowDto;
import com.example.telephony.dialing.dto.table.ExtraCallerVariablesDto;
import com.example.telephony.dialing.persistance.enums.DialCallerStatus;
import com.example.telephony.mapper.CallerMapper;
import com.example.telephony.dialing.service.DialingService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public class DialingTableRowMapper {
    @Autowired
    private DialingService dialingService;

    @Autowired
    private CallerMapper callerMapper;

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

    private List<ExtraCallerVariablesDto> createExtraVariables(DialingCallerResult dialingCallerResult) {
        ExtraCallerVariablesDto status = createExtraValue();
        ExtraCallerVariablesDto answers = createExtraValue();

        if (dialingCallerResult == null) {
            status.setValue(DialCallerStatus.IN_PROGRESS.getMessage());
        } else {
            status.setValue(dialingCallerResult.getStatus().getMessage());
            answers.setValue(StringUtils.join(dialingCallerResult.getAnswers(), ";"));
        }

        return Arrays.asList(status, answers);
    }

    private ExtraCallerVariablesDto createExtraValue() {
        ExtraCallerVariablesDto result = new ExtraCallerVariablesDto();
        result.setId(UUID.randomUUID().toString());
        result.setCreated(new Date());
        result.setValid(true);
        result.setPhoneColumn(false);
        return result;
    }
}
