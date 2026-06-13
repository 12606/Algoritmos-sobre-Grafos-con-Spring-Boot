package com.grafos.algoritmos.graph;

import com.grafos.algoritmos.dto.EdgeDto;
import com.grafos.algoritmos.dto.NodeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Graph {

    private final Map<String, NodeDto> nodes;
    private final Map<String, List<EdgeDto>> adjacencyList;
    private final boolean directed;
    private final boolean weighted;

    public List<EdgeDto> getNeighbors(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, Collections.emptyList());
    }

    public NodeDto getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public boolean containsNode(String nodeId) {
        return nodes.containsKey(nodeId);
    }
}