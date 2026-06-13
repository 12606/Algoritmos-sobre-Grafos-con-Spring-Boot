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
public class DfsService {

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

        Set<String> visited = new LinkedHashSet<>();
        List<String> visitedOrder = new ArrayList<>();
        Map<String, String> parent = new HashMap<>();

        int[] steps = {0};
        boolean found = dfs(graph, start, goal, visited, visitedOrder, parent, steps);

        List<String> path = found && goal != null
                ? GraphUtils.reconstructPath(parent, start, goal)
                : List.of();

        double totalCost = GraphUtils.calculatePathCost(graph, path);

        long endTime = System.nanoTime();

        return AlgorithmResponse.builder()
                .algorithm("DFS")
                .visitedOrder(visitedOrder)
                .path(path)
                .totalCost(totalCost)
                .steps(steps[0])
                .found(found)
                .executionTimeMs((endTime - startTime) / 1_000_000)
                .message(found ? "Camino encontrado con DFS." : "Recorrido DFS finalizado.")
                .build();
    }

    private boolean dfs(
            Graph graph,
            String current,
            String goal,
            Set<String> visited,
            List<String> visitedOrder,
            Map<String, String> parent,
            int[] steps
    ) {
        visited.add(current);
        visitedOrder.add(current);
        steps[0]++;

        if (goal != null && current.equals(goal)) {
            return true;
        }

        for (var edge : graph.getNeighbors(current)) {
            String neighbor = edge.getTo();

            if (!visited.contains(neighbor)) {
                parent.put(neighbor, current);

                boolean found = dfs(
                        graph,
                        neighbor,
                        goal,
                        visited,
                        visitedOrder,
                        parent,
                        steps
                );

                if (found) {
                    return true;
                }
            }
        }

        return false;
    }
}