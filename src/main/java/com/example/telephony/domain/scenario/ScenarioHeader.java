package com.example.telephony.domain.scenario;


import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scenario_header")
@Data
public class ScenarioHeader extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "count_steps")
    private int countSteps;
}
