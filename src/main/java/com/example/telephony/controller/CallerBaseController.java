package com.example.telephony.controller;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallersBaseDto;
import com.example.telephony.mapper.CallersBaseMapper;
import com.example.telephony.service.CallerBaseService;
import io.swagger.annotations.Api;
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
    public List<CallersBaseDto> getAll() {
//        return dialMapper.listDialToListDialDto(dialService.getAll());
        throw new NotImplementedException();
    }

    @GetMapping("{id}")
    public CallersBaseDto getById(@PathVariable("id") Long id) {
        throw new NotImplementedException();
//        return dialMapper.dialToDialDto(dialService.getById(id));
    }

    @PostMapping
    public CallersBaseDto create(@RequestBody CallersBaseDto callersBaseDto) {
        CallersBase callersBase = callersBaseMapper.callersBaseDtoToCallersBase(callersBaseDto);
        throw new NotImplementedException();
//        return dialMapper.dialToDialDto(dialService.create(dial));
    }

    @PutMapping("{id}")
    public CallersBaseDto update(@RequestBody CallersBaseDto callersBaseDto, @PathVariable("id") Long id) throws Exception {
        throw new NotImplementedException();
    }
}
