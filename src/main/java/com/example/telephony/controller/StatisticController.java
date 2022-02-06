package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.dto.CommonStatisticDto;
import com.example.telephony.dto.dialing.charts.pie.DialingResultPieChartDto;
import com.example.telephony.dto.dialing.charts.succes.calls.DialingResultSuccessCallsChartDto;
import com.example.telephony.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "statistic")
@Api("Operations pertaining to statistic")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("common")
    @ApiOperation("Get common statistic by account")
    public CommonStatisticDto getCommonStatistic() {
        return statisticService.createCommonStatistic();
    }

    @GetMapping("success-calls-chart")
    @ApiOperation("Get result for chart success calls")
    public List<DialingResultSuccessCallsChartDto> getSuccessCallsChart() {
        return statisticService.createSuccessChartByAll();
    }

    @GetMapping("pie-chart")
    @ApiOperation("Get result pie chart by accout")
    public DialingResultPieChartDto getPieChartByAccount() {
        return statisticService.createPieChartAllStatistic();
    }
}
