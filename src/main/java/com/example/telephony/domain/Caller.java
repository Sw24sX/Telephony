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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Caller extends BaseEntity {
    @Column(name = "valid")
    private boolean isValid;

    @Column(name = "number")
    private String number;

    @ManyToMany(mappedBy = "callers")
    private List<CallersBase> callersBases;

    @OneToMany(mappedBy = "caller", cascade = CascadeType.REMOVE)
    private List<CallStatistic> callStatistics;

//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb", name = "variables")
//    private Map<String, String> variables;

    @OneToMany(mappedBy = "caller")
    private List<CallerVariable> variables;

    @ManyToOne()
    @JoinColumn(name = "not_valid_column")
    private VariablesTypeName notValidVariable;
}
