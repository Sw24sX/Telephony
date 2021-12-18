package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "question")
@Data
public class ScenarioQuestion extends BaseEntity {
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ScenarioQuestionPart> parts;
}
