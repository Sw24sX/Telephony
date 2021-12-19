package com.example.telephony.domain;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dialing_caller_result")
@Data
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class DialingCallerResult extends BaseEntity {
    @Column(name = "is_hold_on")
    private boolean isHoldOn;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "caller_id")
    private Caller caller;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "dialing_id")
    private Dialing dialing;

    @Column(name = "answers")
    @Type(type = "list-array")
    private List<String> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DialingCallerResult that = (DialingCallerResult) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
