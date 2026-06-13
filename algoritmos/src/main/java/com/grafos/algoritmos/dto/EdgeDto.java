package com.grafos.algoritmos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeDto {

    private String from;
    private String to;

    /*
     * Peso opcional.
     * Si no se envía peso, se toma como 1.0 para búsquedas normales.
     */
    private Double weight;
}