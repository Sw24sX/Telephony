package com.example.telephony.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "caller_base")
public class CallersBase extends BaseEntity {
    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "callersBase")
    private List<Caller> callers;

    @OneToMany(mappedBy = "callersBase")
    List<VariablesTypeName> variablesList;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @OneToOne()
    @JoinColumn(name = "number_id")
    private VariablesTypeName number;
}
