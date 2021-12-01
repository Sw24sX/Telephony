package com.example.telephony.mapper;

import com.example.telephony.domain.ScenarioNode;
import com.example.telephony.domain.ScenarioNodeExtraData;
import com.example.telephony.dto.ScenarioNodeDataDto;
import com.example.telephony.dto.ScenarioNodeDto;
import com.example.telephony.enums.ScenarioNodeTypes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Mapper(componentModel = "spring", uses = {ScenarioNodeDataMapper.class})
public abstract class ScenarioNodeMapper {
    @Autowired
    protected ScenarioNodeDataMapper scenarioNodeDataMapper;

    public ScenarioNode fromScenarioNodeDto(ScenarioNodeDto dto) {
        if (dto == null) {
            return null;
        }

        ScenarioNode scenarioNode = new ScenarioNode();
        scenarioNode.setParents(new ArrayList<>());
        scenarioNode.setChildren(new ArrayList<>());
        scenarioNode.setType(ScenarioNodeTypes.getByExtraName(dto.getType()));
        scenarioNode.setData(scenarioNodeDataMapper.fromScenarioNodeDataDto(dto.getData()));
        //TODO
        scenarioNode.setExtraData(new ScenarioNodeExtraData());
        return scenarioNode;
    }



    @Mappings({
            @Mapping(target = "data", source = "data", resultType = ScenarioNodeDataDto.class)
    })
    public abstract ScenarioNodeDto fromScenarioNode(ScenarioNode scenarioNode);
}
