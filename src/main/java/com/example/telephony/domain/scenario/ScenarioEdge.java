package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "scenario_edge")
@Entity
public class ScenarioEdge extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_node_id")
    private ScenarioNode source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_node_id")
    private ScenarioNode target;

    @Column(name = "answer_key")
    private String answerKey;
}
