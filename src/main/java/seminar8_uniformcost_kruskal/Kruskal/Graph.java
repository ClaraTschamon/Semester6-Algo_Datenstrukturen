package seminar8_uniformcost_kruskal.Kruskal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

    private final Map<String, Vertex> vertexes;
    private final List<Edge> edges;
    private final boolean isDirected;

    public Graph(boolean isDirected) {
        this.vertexes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.isDirected = isDirected;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void loadFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split("\\s+");
            String vertexName1 = parts[0];
            String vertexName2 = parts[1];
            int weight = Integer.parseInt(parts[2]);

            Vertex vertex1 = createVertex(vertexName1);
            Vertex vertex2 = createVertex(vertexName2);

            Edge edge = createEdge(vertex1, vertex2, weight);

            // Add vertex2 as a neighbor of vertex1
            vertex1.addNeighbor(vertex2);
            vertex1.addEdge(edge);

            if (!isDirected) {
                // If undirected, also add vertex1 as a neighbor of vertex2
                vertex2.addNeighbor(vertex1);
                vertex2.addEdge(edge);
            }
        }

        scanner.close();
    }

    public Vertex createVertex(String vertexName) {
        if (!vertexes.containsKey(vertexName)) {
            Vertex newVertex = new Vertex(vertexName);
            vertexes.put(vertexName, newVertex);
        }
        return vertexes.get(vertexName);
    }

    public Vertex getVertex(String vertexName) {
        return vertexes.get(vertexName);
    }

    public List<Vertex> getVertexes() {
        return new ArrayList<>(vertexes.values());
    }

    public Edge createEdge(Vertex source, Vertex destination, int weight) {
        Edge newEdge = new Edge(source, destination, weight);
        edges.add(newEdge);
        return newEdge;
    }

    public void removeEdge(Vertex source, Vertex destination) {
        Edge edge = null;
        for (Edge e : edges) {
            if (e.getVertex1().equals(source) && e.getVertex2().equals(destination)
                    || e.getVertex1().equals(destination) && e.getVertex2().equals(source)) {
                edge = e;
                break;
            }
        }
        if (edge != null) {
            edges.remove(edge);
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(false);

        try {
            graph.loadFromFile("src/main/java/sem8_uniformcost_kruskal/Kruskal/MinimumSpanningTreeGraph.txt");

            // Visualize the graph
            //Vizualizer.visualize(graph);

            // Find the minimum spanning tree with Kruskal's algorithm
            Kruskal kruskal = new Kruskal();
            List<Edge> minimumSpanningTreeEdges = kruskal.findMinimumSpanningTree(graph);

            // Visualize the minimum spanning tree
            Vizualizer.visualizeSpanningTree(graph, minimumSpanningTreeEdges, "Minimum Spanning Tree");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
