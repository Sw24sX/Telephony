package com.example.telephony.mapper;

import com.example.telephony.domain.VariablesTypeName;
import com.example.telephony.dto.CallersBaseColumnDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CallersBaseColumnMapper {

    @Mappings({
            @Mapping(target = "nameInTable", source = "tableName"),
            @Mapping(target = "currentName", source = "currentName"),
            @Mapping(target = "type", source = "type")
    })
    public abstract CallersBaseColumnDto fromVariablesTypeName(VariablesTypeName variablesTypeName);

    public abstract List<CallersBaseColumnDto> fromVariablesTypeName(List<VariablesTypeName> variablesTypeName);

}
