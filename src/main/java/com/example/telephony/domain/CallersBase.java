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

    @OneToMany(mappedBy = "callersBase", cascade = CascadeType.ALL)
    private List<Caller> callers;

    @OneToMany(mappedBy = "callersBase", cascade = CascadeType.ALL)
    List<VariablesTypeName> variablesList;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;
}
