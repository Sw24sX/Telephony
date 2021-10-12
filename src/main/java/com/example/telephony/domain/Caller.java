package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "caller")
@Data
public class Caller extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;
}
