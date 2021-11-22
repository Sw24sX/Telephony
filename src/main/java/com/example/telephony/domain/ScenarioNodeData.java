package com.example.telephony.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "scenario_node_data")
public class ScenarioNodeData extends BaseEntity {
    @Column(name = "sound_path")
    private String soundPath;

    @Column(name = "question")
    private String question;

    @Column(name = "answer_key")
    private String answerKey;

    @Column(name = "need_answer")
    private boolean needAnswer;
}
