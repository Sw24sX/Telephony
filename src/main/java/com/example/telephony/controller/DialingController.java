package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.Dialing;
import com.example.telephony.dto.DialingDto;
import com.example.telephony.dto.DialingStatusDto;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.mapper.DialingMapper;
import com.example.telephony.service.DialingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(GlobalMapping.API + "dialing")
@Api("Operations pertaining to dialing to caller or callers base")
public class DialingController {
    private final DialingService dialingService;
    private final DialingMapper dialingMapper;

    public DialingController(DialingService dialingService, DialingMapper dialingMapper) {
        this.dialingService = dialingService;
        this.dialingMapper = dialingMapper;
    }

    @GetMapping("statuses")
    @ApiOperation("Get all statuses")
    public List<DialingStatusDto> getAllStatuses() {
        return Arrays.stream(DialingStatus.values())
                .map(status -> new DialingStatusDto(status.name(), status.getMessage()))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @ApiOperation("Get dialing by id")
    public DialingDto getDialingById(@PathVariable("id") Long id) {
        return dialingMapper.fromDialing(dialingService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get all dialings")
    public List<DialingDto> getAllDialings() {
        return dialingMapper.fromListDialing(dialingService.getAll());
    }

    @PostMapping()
    @ApiOperation("Create dialing for callers base")
    public DialingDto createDialing(@RequestBody DialingDto dto) {
        Dialing dialing = dialingMapper.fromDialingDto(dto);
        return dialingMapper.fromDialing(dialingService.createDialing(dialing));
    }

    @PutMapping("{id}")
    @ApiOperation("Update dialing")
    public DialingDto updateDialing(@RequestBody DialingDto dto, @PathVariable("id") Long id) {
        Dialing dialing = dialingMapper.fromDialingDto(dto);
        return dialingMapper.fromDialing(dialingService.updateDialing(dialing, id));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete dialing")
    public void deleteDialing(@PathVariable("id") Long id) {
        dialingService.deleteDialing(id);
    }

    @PostMapping("start-dialing/caller")
    @ApiOperation("Start dialing to caller with scenario")
    public void startDialingForCaller(@ApiParam("Caller id") @RequestParam("callerId") Long callerId,
                                      @ApiParam("Scenario id") @RequestParam("scenarioId") Long scenarioId) {
        dialingService.startDialingCaller(callerId, scenarioId);
    }
}
