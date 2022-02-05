package com.example.dialerservice.service.scenario.dialing;

import lombok.Data;

import java.util.Date;

@Data
public class DialingState {
    private Date startDate;
    private Date endDate;
    private Integer countCallersNotDialed;
}
