package com.example.telephony.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "caller_variables")
public class CallerVariable extends BaseEntity {
    @Column(name = "value")
    private String value;

    @ManyToOne()
    @JoinColumn(name = "caller_id")
    private Caller caller;
}
