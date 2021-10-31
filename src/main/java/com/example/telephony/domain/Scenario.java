package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "scenario")
public class Scenario extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "scenario_sound", joinColumns = @JoinColumn(name = "scenario_id"),
            inverseJoinColumns = @JoinColumn(name = "sound_id"))
    private List<Sound> sounds;
}
