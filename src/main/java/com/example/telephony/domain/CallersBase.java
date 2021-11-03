package com.example.telephony.domain;

import com.example.telephony.converter.VariablesJsonConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "caller_base")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CallersBase extends BaseEntity {
    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "caller_base_callers", joinColumns = @JoinColumn(name = "caller_base_id"),
            inverseJoinColumns = @JoinColumn(name = "caller_id"))
    private List<Caller> callers;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "variables")
//    @Convert(converter = VariablesJsonConverter.class)
    private Map<String, String> variables;
}
