package com.example.telephony.domain;

import com.example.telephony.converter.VariablesTypeCodeConverter;
import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.enums.VariablesType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "variables_type_name")
public class VariablesTypeName extends BaseEntity {
    @Column(name = "current_name")
    private String currentName;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "type_code")
    @Convert(converter = VariablesTypeCodeConverter.class)
    private VariablesType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caller_base_id")
    private CallersBase callersBase;
}
