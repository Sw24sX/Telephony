package com.example.telephony.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
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

    @OrderBy("id")
    @OneToMany(mappedBy = "callersBase", cascade = CascadeType.ALL)
    List<VariablesTypeName> variablesList;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updated;
}
