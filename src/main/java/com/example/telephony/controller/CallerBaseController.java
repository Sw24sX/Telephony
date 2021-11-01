package com.example.telephony.controller;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallersBaseDto;
import com.example.telephony.mapper.CallersBaseMapper;
import com.example.telephony.service.CallerBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("callers-base")
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
        return callersBaseMapper.listCallersBaseToListCallersBaseDto(callerBaseService.getAll());
    }

    @GetMapping("{id}")
    @ApiOperation("Get callers base by id")
    public CallersBaseDto getById(@ApiParam("Callers base id") @PathVariable("id") Long id) {
        return callersBaseMapper.callersBaseToCallersBaseDto(callerBaseService.getById(id));
    }

    @PostMapping
    @ApiParam("Create new callers base with new callers only")
    public CallersBaseDto create(@ApiParam("Callers base data") @RequestBody CallersBaseDto callersBaseDto) {
        CallersBase callersBase = callersBaseMapper.callersBaseDtoToCallersBase(callersBaseDto);
        return callersBaseMapper.callersBaseToCallersBaseDto(callerBaseService.create(callersBase));
    }

    @PutMapping("{id}")
    @ApiParam("Update exists callers base. Can add exists callers")
    public CallersBaseDto update(@ApiParam("Callers base data") @RequestBody CallersBaseDto callersBaseDto,
                                 @ApiParam("Callers base id") @PathVariable("id") Long id) {
        CallersBase callersBase = callersBaseMapper.callersBaseDtoToCallersBase(callersBaseDto);
        return callersBaseMapper.callersBaseToCallersBaseDto(callerBaseService.update(id, callersBase));
    }

    @DeleteMapping("{id}")
    @ApiParam("Delete callers base")
    public void delete(@ApiParam("Callers base id") @PathVariable("id") Long id) {
        callerBaseService.deleteCallersBase(id);
    }
}
