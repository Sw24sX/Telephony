package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.service.DialingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "dialing")
@Api("Operations pertaining to dialing to caller or callers base")
public class DialingController {
    private final DialingService dialingService;

    public DialingController(DialingService dialingService) {
        this.dialingService = dialingService;
    }

    @PostMapping("start-dialing/caller")
    @ApiOperation("Start dialing to caller with scenario")
    public void startDialingForCaller(@ApiParam("Caller id") @RequestParam("caller_id") Long callerId,
                                      @ApiParam("Scenario id") @RequestParam("scenarioId") Long scenarioId) {
        dialingService.startDialingCaller(callerId, scenarioId);
    }

    @PostMapping("start-dialing/caller/only")
    @ApiOperation("Start dialing to caller with scenario")
    public void startDialingForCallerOnly(@ApiParam("Caller id") @RequestParam("caller_id") Long callerId) {
        dialingService.callCaller(callerId);
    }

    @PostMapping("start-dialing/callers-base")
    @ApiOperation("Start dialing for callers base with scenario")
    public void startDialingForCallersBase(@ApiParam("Callers base id") @RequestParam("callers_base_id") Long callersBaseId,
                                           @ApiParam("Scenario id") @RequestParam("scenarioId") Long scenarioId) {
        dialingService.startDialingCallersBase(callersBaseId, scenarioId);
    }

    @PostMapping()
    @ApiOperation("Create scheduled dialing")
    public Object createScheduledDialing() {
        //TODO
        throw new NotImplementedException();
    }

    @GetMapping()
    @ApiOperation("Get all dialing")
    public List<Object> getAllDialings() {
        //TODO
        throw new NotImplementedException();
    }
}
