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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "callers_base_id")
    private CallersBase callersBase;

    @OneToMany(mappedBy = "caller", cascade = CascadeType.ALL)
    private List<CallStatistic> callStatistics;

    @OrderBy("typeName")
    @OneToMany(mappedBy = "caller", cascade = CascadeType.ALL)
    private List<CallerVariable> variables;

    @Column(name = "number")
    private int number;

    @Column(name = "is_valid")
    private boolean isValid;
}
