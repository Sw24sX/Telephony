package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scenario_node_position")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioNodePoint extends BaseEntity {
    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;
}
