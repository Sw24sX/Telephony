package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "question_part")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioQuestionPart extends BaseEntity {
    @Column(name = "question_part")
    private String questionPart;

    @Column(name = "is_variable")
    private boolean isVariable;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ScenarioQuestion question;
}
