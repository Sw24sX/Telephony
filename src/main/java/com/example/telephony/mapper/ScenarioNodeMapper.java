package com.example.telephony.mapper;

import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.dto.ScenarioNodeDto;
import com.example.telephony.enums.ScenarioNodeTypes;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public abstract class ScenarioNodeMapper {
    @Autowired
    protected ScenarioNodeDataMapper scenarioNodeDataMapper;

    ScenarioNode fromScenarioNodeDto(ScenarioNodeDto dto) {
        if (dto == null) {
            return null;
        }

        ScenarioNode scenarioNode = new ScenarioNode();
        scenarioNode.setParents(new ArrayList<>());
        scenarioNode.setChildren(new ArrayList<>());
        scenarioNode.setType(ScenarioNodeTypes.getByExtraName(dto.getType()));
        scenarioNode.setData(scenarioNodeDataMapper.fromScenarioNodeDataDto(dto.getData()));
        return scenarioNode;
    }
}
