package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.dto.CallersBaseDto;
import com.example.telephony.dto.CallersBaseHeaderDto;
import com.example.telephony.dto.VariablesTypeDto;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.mapper.CallerMapper;
import com.example.telephony.mapper.CallersBaseHeaderMapper;
import com.example.telephony.mapper.CallersBaseMapper;
import com.example.telephony.service.CallerBaseService;
import com.example.telephony.service.CallerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private final CallersBaseHeaderMapper callersBaseHeaderMapper;
    private final CallerService callerService;
    private final CallerMapper callerMapper;

    @Autowired
    public CallerBaseController(CallerBaseService callerBaseService, CallersBaseMapper callersBaseMapper,
                                CallersBaseHeaderMapper callersBaseHeaderMapper, CallerService callerService,
                                CallerMapper callerMapper) {
        this.callerBaseService = callerBaseService;
        this.callersBaseMapper = callersBaseMapper;
        this.callersBaseHeaderMapper = callersBaseHeaderMapper;
        this.callerService = callerService;
        this.callerMapper = callerMapper;
    }

    @GetMapping
    @ApiOperation("Get all callers bases")
    public List<CallersBaseDto> getAll() {
//        return callersBaseMapper.listCallersBaseToListCallersBaseDto(callerBaseService.getAll());
        return null;
    }

    @GetMapping("header/{id}")
    @ApiOperation("Get callers base by id")
    public CallersBaseHeaderDto getById(@ApiParam("Callers base id") @PathVariable("id") Long id) {
        CallersBase callersBase = callerBaseService.getById(id);
        return callersBaseHeaderMapper.fromCallersBase(callersBase);
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
    public CallersBaseHeaderDto uploadFromExel(@ApiParam("Callers base in exel file") @RequestParam("file") MultipartFile multipartFile,
                                               @ApiParam("Name base") @RequestParam("name") String name) {
        CallersBase callersBase = callerBaseService.uploadFromExelFile(multipartFile, name);
//        return callersBaseMapper.callersBaseToCallersBaseDto(callersBase);
        return callersBaseHeaderMapper.fromCallersBase(callersBase);
    }

    @GetMapping("variables/types")
    @ApiOperation("Get all variables types")
    public List<VariablesTypeDto> getVariablesType() {
        return Arrays.stream(VariablesType.values())
                .map(variablesType -> new VariablesTypeDto(variablesType.name(), variablesType.getDescription()))
                .collect(Collectors.toList());
    }

    @GetMapping("data/{id}")
    @ApiOperation("Get page of callers")
    public Page<CallerDto> getCallersPage(@PathVariable("id") Long callersBaseId,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        Page<Caller> callers = callerService.getPageCallerByCallerBaseId(callersBaseId, page, size);
        return callers.map(callerMapper::fromCaller);
    }
}
