package com.example.telephony.domain;

import com.example.telephony.converter.VariablesJsonConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "caller_base")
public class CallersBase extends BaseEntity {
    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "caller_base_callers", joinColumns = @JoinColumn(name = "caller_base_id"),
            inverseJoinColumns = @JoinColumn(name = "caller_id"))
    private List<Caller> callers;

//    @Column(name = "variables")
//    @Convert(converter = VariablesJsonConverter.class)
//    private Map<String, String> variables;
}
