package seminar5_dijkstra_prim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static seminar5_dijkstra_prim.Vizualizer.visualizeShortestPath;

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

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> dijkstraSingleSourceShortestPath(Vertex startVertex, Vertex endVertex) {
        Map<Vertex, Integer> distances = new HashMap<>();
        for (Vertex vertex : vertexes.values()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(startVertex, 0); // Distance to itself is 0

        // Priority queue to get the vertex with the shortest distance
        PriorityQueue<Vertex> minHeap = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        minHeap.add(startVertex);

        // Map to keep track of the previous vertex in the shortest path
        Map<Vertex, Vertex> previousVertices = new HashMap<>();

        while (!minHeap.isEmpty()) {
            Vertex current = minHeap.poll();
            if (endVertex != null && current == endVertex) {
                break;
            }

            for (Edge edge : current.getEdges()) {
                Vertex neighbor = edge.getVertex1() != current ? edge.getVertex1() : edge.getVertex2();
                int newDistance = distances.get(current) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousVertices.put(neighbor, current);
                    minHeap.add(neighbor);
                }
            }
        }

        List<Vertex> shortestPathVertexes = new ArrayList<>();
        Vertex current = endVertex;
        while (current != null) {
            shortestPathVertexes.add(current);
            current = previousVertices.get(current);
        }
        Collections.reverse(shortestPathVertexes); // Reverse to get path from start to end

        List<Edge> shortestPathEdges = new ArrayList<>(); // get the edges for displaying
        for (int i = 0; i < shortestPathVertexes.size() - 1; i++) {
            Vertex currentVertex = shortestPathVertexes.get(i);
            Vertex nextVertex = shortestPathVertexes.get(i + 1);
            for (Edge edge : currentVertex.getEdges()) {
                if (edge.getVertex1() == nextVertex || edge.getVertex2() == nextVertex) {
                    shortestPathEdges.add(edge);
                    break;
                }
            }
        }

        return shortestPathEdges;
    }

    public List<Edge> primMinimumSpanningTree(Vertex startVertex) {
        Set<Vertex> visited = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        List<Edge> minimumSpanningTree = new ArrayList<>();

        visited.add(startVertex);
        priorityQueue.addAll(startVertex.getEdges());

        while (!priorityQueue.isEmpty()) {
            Edge minEdge = priorityQueue.poll();
            Vertex destinationVertex = minEdge.getVertex1() != startVertex ? minEdge.getVertex1() : minEdge.getVertex2();
            Vertex sourceVertex = minEdge.getVertex1() == startVertex ? minEdge.getVertex1() : minEdge.getVertex2();

            // Skip if both vertices are visited because it will create a cycle
            if (visited.contains(destinationVertex) && visited.contains(sourceVertex)) {
                continue;
            }

            minimumSpanningTree.add(minEdge);

            Vertex nextVertex = visited.contains(destinationVertex) ? sourceVertex : destinationVertex;
            visited.add(nextVertex);
            priorityQueue.addAll(nextVertex.getEdges());
        }

        return minimumSpanningTree;
    }

    public static void main(String[] args) {
        Graph shortestPathGraph = new Graph(false);
        Graph minimumSpanningTreeGraph = new Graph(false);

        try {
            shortestPathGraph.loadFromFile("src/main/java/sem5_dijkstra_prim/ShortestPathGraph.txt");
            minimumSpanningTreeGraph.loadFromFile("src/main/java/sem5_dijkstra_prim/MinimumSpanningTreeGraph.txt");

            var dijkstraShortestPath = shortestPathGraph.dijkstraSingleSourceShortestPath(shortestPathGraph.getVertex("Alice"), shortestPathGraph.getVertex("Franz"));
            var primMinimumSpanningTree = minimumSpanningTreeGraph.primMinimumSpanningTree(minimumSpanningTreeGraph.getVertex("0"));

            visualizeShortestPath(shortestPathGraph, dijkstraShortestPath, "dijkstraShortestPath");
            visualizeShortestPath(minimumSpanningTreeGraph, primMinimumSpanningTree, "primMinimumSpanningTree");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
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
}
