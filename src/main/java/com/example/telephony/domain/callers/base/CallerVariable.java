package com.example.telephony.domain.callers.base;

import com.example.telephony.domain.BaseEntity;
import com.example.telephony.domain.VariablesTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "caller_variables")
public class CallerVariable extends BaseEntity {
    @Column(name = "value")
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caller_id")
    private Caller caller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "variable_id")
    private VariablesTypeName typeName;

    @Column(name = "is_valid")
    private boolean isValid;

    @Column(name = "is_phone_variable")
    private boolean isPhoneColumn;
}
