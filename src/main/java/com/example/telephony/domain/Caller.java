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
    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;

    @ManyToMany(mappedBy = "callers")
    private List<Dial> dials;

    @OneToMany(mappedBy = "caller")
    private List<CallStatistic> callStatistics;
}
