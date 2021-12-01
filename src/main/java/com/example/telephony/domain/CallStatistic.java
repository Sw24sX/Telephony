package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "call_statistic")
@Data
public class CallStatistic extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caller_id")
    private Caller caller;

    @OneToMany(mappedBy = "callStatistic")
    private List<Digit> digits;

    @Column(name = "channel")
    private String channel;
}
