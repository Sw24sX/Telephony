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
@Table(name = "dial")
public class Dial extends BaseEntity {
    @NotNull
    @Column(name = "dialing-start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dialStart;

    @NotNull
    @Column(name = "dialing-end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dialEnd;

    @ManyToMany
    @JoinTable(name = "dial_caller", joinColumns = @JoinColumn(name = "dial_id"),
            inverseJoinColumns = @JoinColumn(name = "caller_id"))
    private List<Caller> callers;
}
