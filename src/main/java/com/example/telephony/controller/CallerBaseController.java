package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallersBaseDto;
import com.example.telephony.dto.VariablesTypeDto;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.mapper.CallersBaseMapper;
import com.example.telephony.service.CallerBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(GlobalMapping.API + "callers-base")
@Api("Operations pertaining to callers bases")
public class CallerBaseController {
    private final CallerBaseService callerBaseService;
    private final CallersBaseMapper callersBaseMapper;

    @Autowired
    public CallerBaseController(CallerBaseService callerBaseService, CallersBaseMapper callersBaseMapper) {
        this.callerBaseService = callerBaseService;
        this.callersBaseMapper = callersBaseMapper;
    }

    @GetMapping
    @ApiOperation("Get all callers bases")
    public List<CallersBaseDto> getAll() {
//        return callersBaseMapper.listCallersBaseToListCallersBaseDto(callerBaseService.getAll());
        return null;
    }

    @GetMapping("{id}")
    @ApiOperation("Get callers base by id")
    public CallersBaseDto getById(@ApiParam("Callers base id") @PathVariable("id") Long id) {
//        return callersBaseMapper.callersBaseToCallersBaseDto(callerBaseService.getById(id));
        return null;
    }

    @PostMapping
    @ApiOperation("Create new callers base with new callers only")
    public CallersBaseDto create(@ApiParam("Callers base data") @RequestBody CallersBaseDto callersBaseDto) {
//        CallersBase callersBase = callersBaseMapper.callersBaseDtoToCallersBase(callersBaseDto);
//        return callersBaseMapper.callersBaseToCallersBaseDto(callerBaseService.create(callersBase));
        return null;
    }

    @PutMapping("{id}")
    @ApiOperation("Update exists callers base. Can add exists callers")
    public CallersBaseDto update(@ApiParam("Callers base data") @RequestBody CallersBaseDto callersBaseDto,
                                 @ApiParam("Callers base id") @PathVariable("id") Long id) {
//        CallersBase callersBase = callersBaseMapper.callersBaseDtoToCallersBase(callersBaseDto);
//        return callersBaseMapper.callersBaseToCallersBaseDto(callerBaseService.update(id, callersBase));
        return null;
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete callers base")
    public void delete(@ApiParam("Callers base id") @PathVariable("id") Long id) {
        callerBaseService.deleteCallersBase(id);
    }

    @PostMapping("upload/exel")
    @ApiOperation("Upload callers base from exel file")
    public CallersBaseDto uploadFromExel(@ApiParam("Callers base in exel file") @RequestParam("file") MultipartFile multipartFile,
                                         @ApiParam("Name base") @RequestParam("name") String name) {
//        CallersBase callersBase = callerBaseService.uploadFromExelFile(multipartFile, name);
//        return callersBaseMapper.callersBaseToCallersBaseDto(callersBase);
        return null;
    }

    @GetMapping("variables/types")
    @ApiOperation("Get all variables types")
    public List<VariablesTypeDto> getVariablesType() {
        return Arrays.stream(VariablesType.values())
                .map(variablesType -> new VariablesTypeDto(variablesType.name(), variablesType.getDescription()))
                .collect(Collectors.toList());
    }
}
