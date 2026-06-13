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
public class IterativeDeepeningService {

    private final GraphBuilder graphBuilder;

    public AlgorithmResponse execute(GraphRequest request) {
        long startTime = System.nanoTime();

        Graph graph = graphBuilder.build(request);
        String start = request.getStart();
        String goal = request.getGoal();

        GraphUtils.validateStart(graph, start);
        GraphUtils.validateGoal(graph, goal);

        int maxDepth;

        if (request.getLimit() != null) {
            if (request.getLimit() < 0) {
                throw new InvalidGraphException("El límite de profundidad debe ser mayor o igual a 0.");
            }

            maxDepth = request.getLimit();
        } else {
            maxDepth = graph.getNodes().size();
        }

        List<String> globalVisitedOrder = new ArrayList<>();
        List<String> finalPath = new ArrayList<>();

        int totalSteps = 0;
        boolean found = false;
        int depthFound = -1;

        for (int depth = 0; depth <= maxDepth; depth++) {
            List<String> localPath = new ArrayList<>();
            Set<String> currentPath = new HashSet<>();
            int[] steps = {0};

            found = depthLimitedSearch(
                    graph,
                    start,
                    goal,
                    0,
                    depth,
                    globalVisitedOrder,
                    localPath,
                    currentPath,
                    steps
            );

            totalSteps += steps[0];

            if (found) {
                finalPath = localPath;
                depthFound = depth;
                break;
            }
        }

        double totalCost = found ? GraphUtils.calculatePathCost(graph, finalPath) : 0.0;

        long endTime = System.nanoTime();

        return AlgorithmResponse.builder()
                .algorithm("Iterative Deepening Search")
                .visitedOrder(globalVisitedOrder)
                .path(found ? finalPath : List.of())
                .totalCost(totalCost)
                .steps(totalSteps)
                .found(found)
                .depthFound(found ? depthFound : null)
                .executionTimeMs((endTime - startTime) / 1_000_000)
                .message(found
                        ? "Camino encontrado con profundidad iterativa."
                        : "No se encontró camino con el límite máximo indicado.")
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