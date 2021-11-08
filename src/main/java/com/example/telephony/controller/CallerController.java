package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.Caller;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.mapper.CallerMapper;
import com.example.telephony.service.CallerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "callers")
@Api("Operations pertaining to caller")
public class CallerController {
    private final CallerService callerService;
    private final CallerMapper callerMapper;

    @Autowired
    public CallerController(CallerService callerService, CallerMapper callerMapper) {
        this.callerService = callerService;
        this.callerMapper = callerMapper;
    }

    @GetMapping()
    @ApiOperation("Get all callers")
    public List<CallerDto> getAll() {
        return callerMapper.listCallerToCallerDto(callerService.getAll());
    }

    @GetMapping("{id}")
    @ApiOperation("Get caller by id")
    public CallerDto getById(@ApiParam("Caller id") @PathVariable("id") Long id) {
        return callerMapper.callerToCallerDto(callerService.getById(id));
    }

    @PostMapping()
    @ApiOperation("Create caller")
    public CallerDto create(@ApiParam("Caller data") @RequestBody CallerDto callerDto) {
        return callerMapper.callerToCallerDto(callerService.create(callerMapper.callerToCallerDto(callerDto)));
    }

    @PostMapping("list")
    @ApiOperation("Create caller list")
    public List<CallerDto> create(@ApiParam("List callers") @RequestBody List<CallerDto> callerDtos) {
        List<Caller> callers = callerMapper.listCallerDtoToCaller(callerDtos);
        return callerMapper.listCallerToCallerDto(callerService.create(callers));
    }

    @PutMapping("{id}")
    @ApiOperation("Update caller")
    public CallerDto update(@ApiParam("Updated caller data") @RequestBody CallerDto callerDto,
                            @ApiParam("Caller id") @PathVariable("id") Long id) {
        Caller caller = callerMapper.callerToCallerDto(callerDto);
        return callerMapper.callerToCallerDto(callerService.update(id, caller));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete caller")
    public void delete(@ApiParam("Caller id") @PathVariable("id") Long id) {
        callerService.delete(id);
    }
}
