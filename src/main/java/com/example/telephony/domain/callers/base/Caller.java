package com.example.telephony.domain.callers.base;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "caller")
@Data
public class Caller extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "callers_base_id")
    private CallersBase callersBase;

    @OrderBy("typeName")
    @OneToMany(mappedBy = "caller", cascade = CascadeType.ALL)
    private List<CallerVariable> variables;

    @Column(name = "number")
    private int number;

    @Column(name = "is_valid")
    private boolean isValid;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Caller caller = (Caller) o;
        return getId() != null && Objects.equals(getId(), caller.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
