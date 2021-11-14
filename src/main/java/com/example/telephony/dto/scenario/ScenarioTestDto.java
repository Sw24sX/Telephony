package com.example.telephony.dto.scenario;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ScenarioTestDto {
    private Map<Integer, Node> nodes;
    private Map<Integer, List<Edge>> edges;
    private Node firstNode;
}
