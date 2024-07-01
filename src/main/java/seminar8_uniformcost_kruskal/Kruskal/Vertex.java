package seminar8_uniformcost_kruskal.Kruskal;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private String name;
    private List<Vertex> neighbors;
    private List<Edge> edges;
    private int degree;

    public Vertex(String name) {
        this.name = name;
        this.neighbors = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Vertex vertex) {
        neighbors.add(vertex);
        degree++;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getDegree() {
        return degree;
    }
}
