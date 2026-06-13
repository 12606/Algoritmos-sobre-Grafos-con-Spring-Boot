package com.grafos.algoritmos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmResponse {

    private String algorithm;

    @Builder.Default
    private List<String> visitedOrder = new ArrayList<>();

    @Builder.Default
    private List<String> path = new ArrayList<>();

    @Builder.Default
    private List<EdgeDto> resultEdges = new ArrayList<>();

    private Double totalCost;
    private Long executionTimeMs;
    private Integer steps;

    private Boolean found;
    private Integer depthFound;

    private String message;
}