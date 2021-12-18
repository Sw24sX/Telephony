package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "question")
@Data
public class ScenarioQuestion extends BaseEntity {
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ScenarioQuestionPart> parts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ScenarioQuestion that = (ScenarioQuestion) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
