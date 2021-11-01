package com.example.telephony.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
}
