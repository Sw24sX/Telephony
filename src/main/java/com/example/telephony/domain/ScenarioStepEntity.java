package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scenario_step")
@Data
public class ScenarioStepEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ScenarioStepEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<ScenarioStepEntity> children;

    @Column(name = "sound_path")
    private String soundPath;

    @Column(name = "question")
    private String question;

    @Column(name = "positive_way")
    private boolean isPositive;

    @Column(name = "index_in_list")
    private int indexInList;
}
