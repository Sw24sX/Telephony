package com.example.telephony.domain;

import javax.persistence.*;

@Entity
@Table(name = "scenario_step")
public class ScenarioStepEntity extends BaseEntity {

    @OneToOne()
    @JoinColumn(name = "next_positive")
    private ScenarioStepEntity nextPositive;

    @OneToOne()
    @JoinColumn(name = "next_negative")
    private ScenarioStepEntity nextNegative;

    @Column(name = "sound_path")
    private String soundPath;

    @Column(name = "question")
    private String question;

    @ManyToOne()
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;
}
