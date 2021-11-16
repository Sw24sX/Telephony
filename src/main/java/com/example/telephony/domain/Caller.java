package com.example.telephony.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "caller")
@Data
public class Caller extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "callers_base_id")
    private CallersBase callersBase;

    @OneToMany(mappedBy = "caller", cascade = CascadeType.REMOVE)
    private List<CallStatistic> callStatistics;

    @OneToMany(mappedBy = "caller")
    private List<CallerVariable> variables;

    @ManyToMany()
    @JoinTable(name = "invalid_variables_caller",
            joinColumns = @JoinColumn(name = "caller_id"),
            inverseJoinColumns = @JoinColumn(name = "caller_variables_id"))
    private List<CallerVariable> invalidVariables;
}
