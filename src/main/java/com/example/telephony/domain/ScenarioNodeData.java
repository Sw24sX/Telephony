package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "scenario_node_data")
public class ScenarioNodeData extends BaseEntity {
    @Column(name = "sound_path")
    private String soundPath;

    @Column(name = "question")
    private String question;

    //todo: delete this
    @Deprecated
    @Column(name = "answer_key")
    private String answerKey;

    @Column(name = "need_answer")
    private boolean needAnswer;
}
