package com.grafos.algoritmos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeDto {

    private String id;

    /*
     * Coordenadas opcionales.
     * Se utilizan principalmente en A* para calcular heurísticas
     * Manhattan o Euclidiana.
     */
    private Double x;
    private Double y;
}