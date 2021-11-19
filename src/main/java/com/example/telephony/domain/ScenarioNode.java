package com.example.telephony.domain;

import com.example.telephony.converter.ScenarioNodeTypeConverter;
import com.example.telephony.enums.ScenarioNodeTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "scenario_node")
@Entity
public class ScenarioNode extends BaseEntity {
    @Column(name = "type_code")
    @Convert(converter = ScenarioNodeTypeConverter.class)
    private ScenarioNodeTypes type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data")
    private ScenarioNodeData data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "extra_data")
    private ScenarioNodeExtraData extraData;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "scenario_edge", joinColumns = @JoinColumn(name = "parent_node_id"),
            inverseJoinColumns = @JoinColumn(name = "child_node_id"))
    private List<ScenarioNode> children;

    @ManyToMany(mappedBy = "children", cascade = CascadeType.ALL)
    private List<ScenarioNode> parents;
}
