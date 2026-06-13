package com.grafos.algoritmos.graph;

import com.grafos.algoritmos.dto.EdgeDto;
import com.grafos.algoritmos.exception.InvalidGraphException;

import java.util.*;

public final class GraphUtils {

    private GraphUtils() {
    }

    public static void validateStart(Graph graph, String start) {
        if (start == null || start.isBlank()) {
            throw new InvalidGraphException("Debe especificarse un nodo de inicio.");
        }

        if (!graph.containsNode(start)) {
            throw new InvalidGraphException("El nodo de inicio no existe en el grafo: " + start);
        }
    }

    public static void validateGoal(Graph graph, String goal) {
        if (goal == null || goal.isBlank()) {
            throw new InvalidGraphException("Debe especificarse un nodo destino.");
        }

        if (!graph.containsNode(goal)) {
            throw new InvalidGraphException("El nodo destino no existe en el grafo: " + goal);
        }
    }

    public static List<String> reconstructPath(
            Map<String, String> parent,
            String start,
            String goal
    ) {
        if (goal == null || start == null) {
            return List.of();
        }

        if (!start.equals(goal) && !parent.containsKey(goal)) {
            return List.of();
        }

        LinkedList<String> path = new LinkedList<>();
        String current = goal;

        while (current != null) {
            path.addFirst(current);

            if (current.equals(start)) {
                break;
            }

            current = parent.get(current);
        }

        if (path.isEmpty() || !path.getFirst().equals(start)) {
            return List.of();
        }

        return path;
    }

    public static double calculatePathCost(Graph graph, List<String> path) {
        if (path == null || path.size() < 2) {
            return 0.0;
        }

        double total = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);

            Optional<EdgeDto> edge = graph.getNeighbors(from)
                    .stream()
                    .filter(e -> Objects.equals(e.getTo(), to))
                    .findFirst();

            if (edge.isPresent()) {
                total += edge.get().getWeight() != null ? edge.get().getWeight() : 1.0;
            }
        }

        return total;
    }

    public static void validateUndirectedWeightedGraph(Graph graph) {
        if (graph.isDirected()) {
            throw new InvalidGraphException("Este algoritmo requiere un grafo no dirigido.");
        }

        if (!graph.isWeighted()) {
            throw new InvalidGraphException("Este algoritmo requiere un grafo ponderado.");
        }
    }

    public static void validateNonNegativeWeights(List<EdgeDto> edges) {
        if (edges == null) {
            return;
        }

        for (EdgeDto edge : edges) {
            if (edge.getWeight() != null && edge.getWeight() < 0) {
                throw new InvalidGraphException("No se permiten pesos negativos en este algoritmo.");
            }
        }
    }
}