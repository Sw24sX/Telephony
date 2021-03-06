package com.example.telephony.controller;

import com.example.telephony.common.GlobalMappingUtils;
import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.dto.caller.base.CallerDto;
import com.example.telephony.dto.caller.base.CallersBaseHeaderDto;
import com.example.telephony.dto.VariablesTypeDto;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.enums.VariablesType;
import com.example.telephony.mapper.CallerMapper;
import com.example.telephony.mapper.CallersBaseHeaderMapper;
import com.example.telephony.service.CallerBaseService;
import com.example.telephony.service.CallerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(GlobalMappingUtils.API + "callers-base")
@Api("Operations pertaining to callers bases")
public class CallerBaseController {
    private static final String CALLERS_BASE_ID = "Callers base id";
    public static final String PATH_ID = "id";

    private final CallerBaseService callerBaseService;
    private final CallersBaseHeaderMapper callersBaseHeaderMapper;
    private final CallerService callerService;
    private final CallerMapper callerMapper;

    @Autowired
    public CallerBaseController(CallerBaseService callerBaseService, CallersBaseHeaderMapper callersBaseHeaderMapper,
                                CallerService callerService, CallerMapper callerMapper) {
        this.callerBaseService = callerBaseService;
        this.callersBaseHeaderMapper = callersBaseHeaderMapper;
        this.callerService = callerService;
        this.callerMapper = callerMapper;
    }

    @GetMapping("header")
    @ApiOperation("Get all confirmed callers bases")
    public Page<CallersBaseHeaderDto> getAll(@ApiParam("Number page") @RequestParam("page") int page,
                                             @ApiParam("Page size") @RequestParam("size") int size,
                                             @ApiParam("Sort direction") @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
                                             @ApiParam("Sort field") @RequestParam(value = "sortBy", required = false, defaultValue = "NAME") FieldsPageSort sort,
                                             @ApiParam("Filtering by name") @RequestParam(value = "name", required = false, defaultValue = "") String searchedName) {
        Page<CallersBase> callersBases = callerBaseService.getPage(page, size, sort, direction, searchedName);
        return callersBases.map(callersBaseHeaderMapper::fromCallersBase);
    }

    @GetMapping("header/{id}")
    @ApiOperation("Get callers base by id")
    public CallersBaseHeaderDto getById(@ApiParam(CALLERS_BASE_ID) @PathVariable(PATH_ID) Long id) {
        CallersBase callersBase = callerBaseService.getById(id);
        return callersBaseHeaderMapper.fromCallersBase(callersBase);
    }

    @PutMapping("header/{id}")
    @ApiOperation("Update exists callers base. Can add exists callers")
    public CallersBaseHeaderDto update(@ApiParam("Callers base data") @Valid @RequestBody CallersBaseHeaderDto callersBaseHeaderDto,
                                       @ApiParam(CALLERS_BASE_ID) @PathVariable(PATH_ID) Long id) {
        CallersBase callersBase = callersBaseHeaderMapper.fromCallersBaseHeaderDto(callersBaseHeaderDto);
        return callersBaseHeaderMapper.fromCallersBase(callerBaseService.update(id, callersBase));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete callers base")
    public void delete(@ApiParam(CALLERS_BASE_ID) @PathVariable(PATH_ID) Long id) {
        callerBaseService.deleteCallersBase(id);
    }

    @PostMapping("upload/exel")
    @ApiOperation("Upload callers base from exel file")
    public CallersBaseHeaderDto uploadFromExel(@ApiParam("Callers base in exel file") @RequestParam("file") MultipartFile multipartFile,
                                               @ApiParam("Name base") @RequestParam("name") String name) {
        CallersBase callersBase = callerBaseService.uploadFromExelFile(multipartFile, name);
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
    public Page<CallerDto> getCallersPage(@ApiParam(CALLERS_BASE_ID) @PathVariable(PATH_ID) Long callersBaseId,
                                          @ApiParam("Page number. Start with 0") @RequestParam("page") Integer page,
                                          @ApiParam("Page size") @RequestParam("size") Integer size,
                                          @ApiParam("Return only invalid callers")
                                              @RequestParam(value = "onlyInvalid", required = false, defaultValue = "false")
                                              boolean onlyInvalid) {
        Page<Caller> callers = callerService.getPageCallerByCallerBaseId(callersBaseId, page, size, onlyInvalid);
        return callers.map(callerMapper::fromCaller);
    }
}
