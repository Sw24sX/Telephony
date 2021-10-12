package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
@Data
@EqualsAndHashCode(of = {"id"})
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    private Long id;
}
