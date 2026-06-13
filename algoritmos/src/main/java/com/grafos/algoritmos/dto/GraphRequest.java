package com.grafos.algoritmos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphRequest {

    private Boolean directed;
    private Boolean weighted;

    private String start;
    private String goal;

    /*
     * Se usa en profundidad limitada e iterativa.
     */
    private Integer limit;

    /*
     * Para A*:
     * NONE
     * MANHATTAN
     * EUCLIDEAN
     */
    private String heuristic;

    private List<NodeDto> nodes;
    private List<EdgeDto> edges;

    public boolean isDirectedGraph() {
        return Boolean.TRUE.equals(directed);
    }

    public boolean isWeightedGraph() {
        return Boolean.TRUE.equals(weighted);
    }
}