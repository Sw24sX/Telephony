package com.example.telephony.tts.persistance.domain;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "generated_sound")
public class GeneratedSound extends BaseEntity {
    @Column(name = "uri")
    private String uri;

    @Column(name = "path")
    private String path;
}
