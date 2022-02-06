package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ScenarioEdge edge = (ScenarioEdge) o;
        return getId() != null && Objects.equals(getId(), edge.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
