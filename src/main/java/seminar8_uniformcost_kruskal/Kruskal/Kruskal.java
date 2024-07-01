package seminar8_uniformcost_kruskal.Kruskal;

import java.util.*;

public class Kruskal {

    // der Kruskal Algorithmus ist ein Algorithmus zur Bestimmung des minimalen Spannbaums eines zusammenhängenden, ungerichteten Graphen.
    // Funktionsweise: Zuerst alle Kanten nach Gewicht sortieren. Dann hinzufügen
    // der aktuell kürzesten Kante. Überprüfen, ob ein Zyklus dadurch entsteht.

    public List<Edge> findMinimumSpanningTree(Graph graph) {

        // Sort all edges by weight
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        sortedEdges.sort(Comparator.comparingInt(Edge::getWeight));
        List<Edge> minimumSpanningTreeEdges = new ArrayList<>();

        Graph minimumSpanningTree = new Graph(false);
        for (Vertex vertex : graph.getVertexes()) {
            minimumSpanningTree.createVertex(vertex.getName());
        }

        UnionFind<Vertex> unionFind = new UnionFind<>();
        for (Vertex vertex : graph.getVertexes()) {
            unionFind.add(vertex);
        }

        for (Edge edge : sortedEdges) {
            Vertex vertex1 = edge.getVertex1();
            Vertex vertex2 = edge.getVertex2();

            if (unionFind.find(vertex1) != unionFind.find(vertex2)) {
                minimumSpanningTree.createEdge(
                        minimumSpanningTree.getVertex(vertex1.getName()),
                        minimumSpanningTree.getVertex(vertex2.getName()),
                        edge.getWeight()
                );
                unionFind.union(vertex1, vertex2);
                minimumSpanningTreeEdges.add(edge);
            }
        }

        return minimumSpanningTreeEdges;
    }
}
