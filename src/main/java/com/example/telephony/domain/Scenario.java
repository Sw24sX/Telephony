package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "scenario")
public class Scenario extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToOne()
    @JoinColumn(name = "first_step")
    private ScenarioStepEntity scenarioStep;

    @OneToMany(mappedBy = "scenario")
    private List<ScenarioStepEntity> scenarioStepEntities;
}
