package com.example.telephony.mapper;

import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.dto.caller.base.CallersBaseColumnDto;
import com.example.telephony.repository.VariablesTypeNameRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CallersBaseColumnMapper {

    @Autowired
    protected VariablesTypeNameRepository variablesTypeNameRepository;

    @Mappings({
            @Mapping(target = "nameInTable", source = "tableName"),
            @Mapping(target = "currentName", source = "currentName"),
            @Mapping(target = "type", source = "type")
    })
    public abstract CallersBaseColumnDto fromVariablesTypeName(VariablesTypeName variablesTypeName);

    public abstract List<CallersBaseColumnDto> fromVariablesTypeName(List<VariablesTypeName> variablesTypeName);

    public VariablesTypeName fromCallersBaseColumnDto(CallersBaseColumnDto callersBaseColumnDto) {
        if(callersBaseColumnDto == null) {
            return null;
        }

        VariablesTypeName variablesTypeName = variablesTypeNameRepository
                .findById(callersBaseColumnDto.getId()).orElse(null);
        if (variablesTypeName == null) {
            return null;
        }

        BeanUtils.copyProperties(callersBaseColumnDto, variablesTypeName);
        return variablesTypeName;
    }

    public abstract List<VariablesTypeName> fromCallersBaseColumnDto(List<CallersBaseColumnDto> callersBaseColumnDtos);
}
