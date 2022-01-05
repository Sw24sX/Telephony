package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.CallersBaseColumnDto;
import com.example.telephony.dto.CallersBaseHeaderDto;
import com.example.telephony.dto.dialing.table.DialingResultTableDto;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.mapper.CallersBaseHeaderMapper;
import com.example.telephony.service.CallerBaseService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DialingTableHeaderMapper {
    @Autowired
    protected CallersBaseHeaderMapper callersBaseHeaderMapper;

    @Autowired
    protected CallerBaseService callerBaseService;

    public DialingResultTableDto fromDialing(Dialing dialing) {
        if (dialing == null) {
            return null;
        }

        DialingResultTableDto dialingResultTableDto = new DialingResultTableDto();

        CallersBase callersBase = callerBaseService.getById(dialing.getCallersBaseId());
        CallersBaseHeaderDto callersBaseHeaderDto = callersBaseHeaderMapper.fromCallersBase(callersBase);

        dialingResultTableDto.setOriginal(callersBaseHeaderDto.getColumns());
        dialingResultTableDto.setExtra(createExtraColumns());
        return dialingResultTableDto;
    }

    private List<CallersBaseColumnDto> createExtraColumns() {
        CallersBaseColumnDto status = new CallersBaseColumnDto();
        status.setCurrentName("Статус");
        status.setNameInTable("Статус");
        status.setType(VariablesType.STRING);

        CallersBaseColumnDto answers = new CallersBaseColumnDto();
        answers.setCurrentName("Ответы");
        answers.setNameInTable("Ответы");
        answers.setType(VariablesType.STRING);

        return Arrays.asList(status, answers);
    }
}
