package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.caller.base.CallersBaseHeaderDto;
import com.example.telephony.dto.dialing.table.DialingResultTableDto;
import com.example.telephony.dto.dialing.table.ExtraCallerBaseColumnDto;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.mapper.CallersBaseHeaderMapper;
import com.example.telephony.service.CallerBaseService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Mapper(componentModel = "spring")
public class DialingTableHeaderMapper {
    @Autowired
    private CallersBaseHeaderMapper callersBaseHeaderMapper;

    @Autowired
    private CallerBaseService callerBaseService;

    public DialingResultTableDto fromDialing(Dialing dialing) {
        if (dialing == null) {
            return null;
        }

        DialingResultTableDto dialingResultTableDto = new DialingResultTableDto();

        CallersBase callersBase = callerBaseService.getById(dialing.getCallersBaseId());
        CallersBaseHeaderDto callersBaseHeaderDto = callersBaseHeaderMapper.fromCallersBase(callersBase);

        dialingResultTableDto.setOriginal(callersBaseHeaderDto.getColumns());
        dialingResultTableDto.setExtra(Arrays.asList(createExtraColumn("Статус"), createExtraColumn("Ответы")));
        return dialingResultTableDto;
    }

    private ExtraCallerBaseColumnDto createExtraColumn(String name) {
        ExtraCallerBaseColumnDto result = new ExtraCallerBaseColumnDto();
        result.setId(UUID.randomUUID().toString());
        result.setCreated(new Date());
        result.setCurrentName(name);
        result.setNameInTable(name);
        result.setType(VariablesType.STRING);
        return result;
    }
}
