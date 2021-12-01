package com.example.telephony.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "scenario_node_extra_data")
@Data
public class ScenarioNodeExtraData extends BaseEntity{
    //TODO
}
