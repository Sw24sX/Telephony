package com.example.telephony.controller;

import com.example.telephony.domain.Dial;
import com.example.telephony.dto.DialDto;
import com.example.telephony.mapper.DialMapper;
import com.example.telephony.service.DialService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dial")
@Api("Operations pertaining to dial")
public class DialController {
    private final DialService dialService;
    private final DialMapper dialMapper;

    @Autowired
    public DialController(DialService dialService, DialMapper dialMapper) {
        this.dialService = dialService;
        this.dialMapper = dialMapper;
    }

    @GetMapping
    public List<DialDto> getAll() {
//        return dialMapper.listDialToListDialDto(dialService.getAll());
        throw new NotImplementedException();
    }

    @GetMapping("{id}")
    public DialDto getById(@PathVariable("id") Long id) {
        throw new NotImplementedException();
//        return dialMapper.dialToDialDto(dialService.getById(id));
    }

    @PostMapping
    public DialDto create(@RequestBody DialDto dialDto) {
        Dial dial = dialMapper.dialDtoToDial(dialDto);
        throw new NotImplementedException();
//        return dialMapper.dialToDialDto(dialService.create(dial));
    }

    @PutMapping("{id}")
    public DialDto update(@RequestBody DialDto dialDto, @PathVariable("id") Long id) throws Exception {
        throw new NotImplementedException();
    }
}
