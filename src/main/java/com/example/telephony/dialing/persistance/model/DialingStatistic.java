package com.example.telephony.dialing.persistance.model;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dialing_statistic")
@Data
public class DialingStatistic extends BaseEntity {
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "dialing_id")
    private Dialing dialing;
}
