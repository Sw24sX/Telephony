package com.example.telephony.domain.scenario;

import com.example.telephony.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scenario_node_extra_data")
@Data
public class ScenarioNodeExtraData extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position")
    private ScenarioNodePoint position;
}
