package com.grafos.algoritmos.service;

import com.grafos.algoritmos.dto.AlgorithmResponse;
import com.grafos.algoritmos.dto.GraphRequest;
import com.grafos.algoritmos.graph.Graph;
import com.grafos.algoritmos.graph.GraphBuilder;
import com.grafos.algoritmos.graph.GraphUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BfsService {

    private final GraphBuilder graphBuilder;

    public AlgorithmResponse execute(GraphRequest request) {
        long startTime = System.nanoTime();

        Graph graph = graphBuilder.build(request);
        String start = request.getStart();
        String goal = request.getGoal();

        GraphUtils.validateStart(graph, start);

        if (goal != null && !goal.isBlank()) {
            GraphUtils.validateGoal(graph, goal);
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new LinkedHashSet<>();
        List<String> visitedOrder = new ArrayList<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        int steps = 0;
        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitedOrder.add(current);
            steps++;

            if (goal != null && current.equals(goal)) {
                found = true;
                break;
            }

            for (var edge : graph.getNeighbors(current)) {
                String neighbor = edge.getTo();

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<String> path = found && goal != null
                ? GraphUtils.reconstructPath(parent, start, goal)
                : List.of();

        double totalCost = GraphUtils.calculatePathCost(graph, path);

        long endTime = System.nanoTime();

        return AlgorithmResponse.builder()
                .algorithm("BFS")
                .visitedOrder(visitedOrder)
                .path(path)
                .totalCost(totalCost)
                .steps(steps)
                .found(found)
                .executionTimeMs((endTime - startTime) / 1_000_000)
                .message(found ? "Camino encontrado con BFS." : "Recorrido BFS finalizado.")
                .build();
    }
}