package com.grafos.algoritmos.graph;

import com.grafos.algoritmos.dto.EdgeDto;
import com.grafos.algoritmos.dto.GraphRequest;
import com.grafos.algoritmos.dto.NodeDto;
import com.grafos.algoritmos.exception.InvalidGraphException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphBuilder {

    public Graph build(GraphRequest request) {
        validateBasicRequest(request);

        boolean directed = request.isDirectedGraph();
        boolean weighted = request.isWeightedGraph();

        Map<String, NodeDto> nodes = new LinkedHashMap<>();
        Map<String, List<EdgeDto>> adjacencyList = new LinkedHashMap<>();

        for (NodeDto node : request.getNodes()) {
            String nodeId = normalize(node.getId());

            if (nodeId == null || nodeId.isBlank()) {
                throw new InvalidGraphException("Existe un nodo sin identificador válido.");
            }

            if (nodes.containsKey(nodeId)) {
                throw new InvalidGraphException("El nodo '" + nodeId + "' está duplicado.");
            }

            node.setId(nodeId);
            nodes.put(nodeId, node);
            adjacencyList.put(nodeId, new ArrayList<>());
        }

        if (request.getEdges() != null) {
            for (EdgeDto edge : request.getEdges()) {
                validateEdge(edge, nodes);

                String from = normalize(edge.getFrom());
                String to = normalize(edge.getTo());
                Double weight = edge.getWeight() != null ? edge.getWeight() : 1.0;

                EdgeDto normalizedEdge = new EdgeDto(from, to, weight);
                adjacencyList.get(from).add(normalizedEdge);

                if (!directed) {
                    EdgeDto reverseEdge = new EdgeDto(to, from, weight);
                    adjacencyList.get(to).add(reverseEdge);
                }
            }
        }

        return new Graph(nodes, adjacencyList, directed, weighted);
    }

    private void validateBasicRequest(GraphRequest request) {
        if (request == null) {
            throw new InvalidGraphException("La petición del grafo no puede ser nula.");
        }

        if (request.getNodes() == null || request.getNodes().isEmpty()) {
            throw new InvalidGraphException("El grafo debe contener al menos un nodo.");
        }
    }

    private void validateEdge(EdgeDto edge, Map<String, NodeDto> nodes) {
        if (edge == null) {
            throw new InvalidGraphException("Existe una arista nula en el grafo.");
        }

        String from = normalize(edge.getFrom());
        String to = normalize(edge.getTo());

        if (from == null || from.isBlank()) {
            throw new InvalidGraphException("Existe una arista sin nodo de origen válido.");
        }

        if (to == null || to.isBlank()) {
            throw new InvalidGraphException("Existe una arista sin nodo destino válido.");
        }

        if (!nodes.containsKey(from)) {
            throw new InvalidGraphException("La arista contiene un nodo origen no definido: " + from);
        }

        if (!nodes.containsKey(to)) {
            throw new InvalidGraphException("La arista contiene un nodo destino no definido: " + to);
        }
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}