package com.grafos.algoritmos.service;

import com.grafos.algoritmos.dto.AlgorithmResponse;
import com.grafos.algoritmos.dto.GraphRequest;
import com.grafos.algoritmos.exception.InvalidGraphException;
import com.grafos.algoritmos.graph.Graph;
import com.grafos.algoritmos.graph.GraphBuilder;
import com.grafos.algoritmos.graph.GraphUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DepthLimitedService {

    private final GraphBuilder graphBuilder;

    public AlgorithmResponse execute(GraphRequest request) {
        long startTime = System.nanoTime();

        Graph graph = graphBuilder.build(request);
        String start = request.getStart();
        String goal = request.getGoal();

        GraphUtils.validateStart(graph, start);
        GraphUtils.validateGoal(graph, goal);

        if (request.getLimit() == null || request.getLimit() < 0) {
            throw new InvalidGraphException("El límite de profundidad debe ser mayor o igual a 0.");
        }

        int limit = request.getLimit();

        List<String> visitedOrder = new ArrayList<>();
        List<String> path = new ArrayList<>();
        Set<String> currentPath = new HashSet<>();

        int[] steps = {0};

        boolean found = depthLimitedSearch(
                graph,
                start,
                goal,
                0,
                limit,
                visitedOrder,
                path,
                currentPath,
                steps
        );

        double totalCost = found ? GraphUtils.calculatePathCost(graph, path) : 0.0;

        long endTime = System.nanoTime();

        return AlgorithmResponse.builder()
                .algorithm("Depth Limited Search")
                .visitedOrder(visitedOrder)
                .path(found ? path : List.of())
                .totalCost(totalCost)
                .steps(steps[0])
                .found(found)
                .executionTimeMs((endTime - startTime) / 1_000_000)
                .message(found
                        ? "Camino encontrado dentro del límite de profundidad."
                        : "No se encontró camino dentro del límite indicado.")
                .build();
    }

    private boolean depthLimitedSearch(
            Graph graph,
            String current,
            String goal,
            int depth,
            int limit,
            List<String> visitedOrder,
            List<String> path,
            Set<String> currentPath,
            int[] steps
    ) {
        visitedOrder.add(current);
        path.add(current);
        currentPath.add(current);
        steps[0]++;

        if (current.equals(goal)) {
            return true;
        }

        if (depth == limit) {
            path.remove(path.size() - 1);
            currentPath.remove(current);
            return false;
        }

        for (var edge : graph.getNeighbors(current)) {
            String neighbor = edge.getTo();

            if (!currentPath.contains(neighbor)) {
                boolean found = depthLimitedSearch(
                        graph,
                        neighbor,
                        goal,
                        depth + 1,
                        limit,
                        visitedOrder,
                        path,
                        currentPath,
                        steps
                );

                if (found) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        currentPath.remove(current);

        return false;
    }
}