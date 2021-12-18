package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private ScenarioQuestion question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ScenarioQuestionPart that = (ScenarioQuestionPart) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
