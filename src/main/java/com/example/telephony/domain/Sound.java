package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "sound")
@NoArgsConstructor
public class Sound extends BaseEntity {
    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "blob")
    private byte[] blob;
}
