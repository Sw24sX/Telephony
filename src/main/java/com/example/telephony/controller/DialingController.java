package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.dto.dialing.DialingDto;
import com.example.telephony.dto.dialing.DialingStatusDto;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.mapper.dialing.DialingMapper;
import com.example.telephony.service.DialingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public Page<DialingDto> getAllDialings(@ApiParam("Number page") @RequestParam("page") int page,
                                           @ApiParam("Page size") @RequestParam("size") int size,
                                           @ApiParam("Sort direction") @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
                                           @ApiParam("Sort field") @RequestParam(value = "sortBy", required = false, defaultValue = "NAME") FieldsPageSort sort,
                                           @ApiParam("Filtering by name") @RequestParam(value = "name", required = false, defaultValue = "") String searchedName,
                                           @ApiParam("Filtering by status") @RequestParam(value = "status", required = false) DialingStatus status) {
        Page<Dialing> dialing = dialingService.getPage(page, size, sort, direction, searchedName, status);
        return dialing.map(dialingMapper::fromDialing);
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

    @GetMapping("callers-base/{callers_base_id}")
    @ApiOperation("Get all dialings by callers base id")
    public List<DialingDto> getDialingsByCallersBaseId(@ApiParam("Callers base id") @PathVariable("callers_base_id") Long callersBaseId) {
        List<Dialing> dialings = dialingService.getDialingsByCallersBaseId(callersBaseId);
        return dialingMapper.fromListDialing(dialings);
    }

    @PostMapping("scheduled/{id}/start")
    @ApiOperation("Start scheduled dialing now")
    public void startScheduledDialing(@PathVariable("id") Long id) {
        dialingService.startScheduledDialingNow(id);
    }
}
