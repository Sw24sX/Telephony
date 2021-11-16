package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "caller")
@Data
public class Caller extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "callers_base_id")
    private CallersBase callersBase;

    @OneToMany(mappedBy = "caller", cascade = CascadeType.REMOVE)
    private List<CallStatistic> callStatistics;

    @OneToMany(mappedBy = "caller")
    private List<CallerVariable> variables;
}
