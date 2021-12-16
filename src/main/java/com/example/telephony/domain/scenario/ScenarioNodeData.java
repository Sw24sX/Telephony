package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "scenario_node_data")
public class ScenarioNodeData extends BaseEntity {
    @Column(name = "sound_path")
    private String soundPath;

    @OneToOne
    @JoinColumn(name = "question_id")
    private ScenarioQuestion question;

    @Column(name = "need_answer")
    private boolean needAnswer;

    @Column(name = "waiting_time")
    private int waitingTime;
}
