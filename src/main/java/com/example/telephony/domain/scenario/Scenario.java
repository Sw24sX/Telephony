package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "scenario")
public class Scenario extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "root_node")
    private ScenarioNode root;

    @Column(name = "count_steps")
    private int countSteps;

    @Column(name = "last_connected_callers_base_id")
    private Long lastConnectedCallersBaseId;
}
