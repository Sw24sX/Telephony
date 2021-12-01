package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "call_statistic_digit")
@Data
public class Digit extends BaseEntity {
    @Column(name = "digit")
    private String digit;

    @ManyToOne
    @JoinColumn(name = "call_statistic_id")
    private CallStatistic callStatistic;
}
