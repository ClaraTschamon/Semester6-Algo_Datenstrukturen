package seminar4_graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static seminar4_graphs.Vizualizer.visualize;
import static seminar4_graphs.Vizualizer.visualizeShortestPath;

public class Graph {

    private final Map<String, Vertex> vertexes;
    private final List<Edge> edges;
    private final boolean isDirected;

    private final List<Vertex> eulerianPath = new ArrayList<>();

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

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isConnected() { // ist der Graph zusammenhängend?
        Set<Vertex> closedList = new HashSet<>();
        if (vertexes.isEmpty()) {
            return false;
        }
        depthFirstSearch(vertexes.values().iterator().next(), closedList);
        return closedList.size() == vertexes.size(); // wenn alle Knoten besucht wurden, ist der Graph zusammenhängend
    }

    public void depthFirstSearch(Vertex vertex, Set<Vertex> closedList) {
        closedList.add(vertex);
        for (Vertex neighbor : vertex.getNeighbors()) {
            if (!closedList.contains(neighbor)) {
                depthFirstSearch(neighbor, closedList);
            }
        }
    }

    void dfsEulerianPath(Vertex vertex) {

        // Visit each edge connected to the current vertex until all edges have been visited
        while (vertex.getEdges().stream().anyMatch(e -> !e.isVisited())) {

            // Get the next unvisited edge
            Edge edge = vertex.getEdges().stream().filter(e -> !e.isVisited()).findFirst().orElse(null);
            if (edge == null) {
                break;
            }

            // Mark the edge as visited
            edge.setVisited(true);

            // Determine the next vertex based on the edge direction
            Vertex nextVertex = (edge.getVertex1() == vertex) ? edge.getVertex2() : edge.getVertex1();
            // set the edge that was used to get to the next vertex as visited
            nextVertex.getEdges().stream().filter(e -> e.getVertex2().equals(vertex)).forEach(e -> e.setVisited(true));
            nextVertex.getEdges().stream().filter(e -> e.getVertex1().equals(vertex)).forEach(e -> e.setVisited(true));

            // Recursively visit the next vertex
            System.out.println("Visiting vertex: " + nextVertex.getName() + " taking edge: " + edge.getVertex1().getName() + " -> " + edge.getVertex2().getName());
            dfsEulerianPath(nextVertex);
        }

        // Add the current vertex to the Eulerian path after processing all edges
        eulerianPath.add(vertex);
    }

    public List<Vertex> findEulerianPath(Vertex startVertex) {
        dfsEulerianPath(startVertex);
        return eulerianPath;
    }

    public List<Vertex> findShortestPathDFS(Vertex start, Vertex end) {
        // Use a map to store the current shortest path found to each vertex
        Map<Vertex, List<Vertex>> shortestPaths = new HashMap<>();
        List<Vertex> closedList = new ArrayList<>();
        closedList.add(start);
        shortestPaths.put(start, closedList);

        // Perform DFS to find the shortest path
        dfsShortestPath(start, end, new HashSet<>(), shortestPaths);

        // Retrieve the shortest path to the end vertex if it exists
        return shortestPaths.get(end);
    }

    private void dfsShortestPath(Vertex current, Vertex end, Set<Vertex> closedList,
                                 Map<Vertex, List<Vertex>> shortestPaths) {
        if (current == end) {
            return; // Reached the destination
        }

        closedList.add(current);

        for (Edge edge : current.getEdges()) {
            Vertex neighbor = edge.getVertex1() != current ? edge.getVertex1() : edge.getVertex2();

            if (!closedList.contains(neighbor)) {
                // Calculate the new path length
                List<Vertex> currentPath = new ArrayList<>(shortestPaths.get(current));
                currentPath.add(neighbor);

                // Update the shortest path to the neighbor if this path is shorter
                if (!shortestPaths.containsKey(neighbor) ||
                        getTotalWeight(currentPath) < getTotalWeight(shortestPaths.get(neighbor))) {
                    shortestPaths.put(neighbor, currentPath);
                    dfsShortestPath(neighbor, end, closedList, shortestPaths);
                }
            }
        }

        closedList.remove(current); // Backtrack
    }

    private int getTotalWeight(List<Vertex> path) {
        // Calculate the total weight (sum of edge weights) of the path
        int totalWeight = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex current = path.get(i);
            Vertex next = path.get(i + 1);
            Edge connectingEdge = current.getEdges().stream()
                    .filter(e -> e.getVertex1().equals(next) || e.getVertex2().equals(next))
                    .findFirst().orElse(null);
            if (connectingEdge != null) {
                totalWeight += connectingEdge.getWeight();
            }
        }
        return totalWeight;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(false);

        try {
            graph.loadFromFile("src/main/java/sem4_graphs/ShortestPathGraph.txt");

            // Display vertexes and edges
            System.out.println("Vertexes:");
            for (Vertex vertex : graph.getVertexes()) {
                System.out.println("Vertex " + vertex.getName());
            }

            System.out.println("\nEdges:");
            for (Edge edge : graph.getEdges()) {
                System.out.println("Edge from " + edge.getVertex1().getName() +
                        " to " + edge.getVertex2().getName() +
                        " - Weight: " + edge.getWeight());
            }

            List<Vertex> neighborsOfAlice = graph.getVertex("Alice").getNeighbors();
            System.out.println("\nNeighbors of Alice:");
            for (Vertex vertex : neighborsOfAlice) {
                System.out.println(vertex.getName());
            }

            // Find shortest path
            var vertex1 = graph.getVertex("Charlie");
            var vertex2 = graph.getVertex("Bob");
            var dfsShortestPath = graph.findShortestPathDFS(vertex1, vertex2);
            var length = graph.getTotalWeight(dfsShortestPath);

            System.out.println("\nShortest path from: " + vertex1.getName() + " to " + vertex2.getName() + " with length: " + length);
            for (Vertex vertex : dfsShortestPath) {
                System.out.println(vertex.getName());
            }

            // Visualize the graph
            visualize(graph);

            // Visualize the shortest path
            visualizeShortestPath(graph, dfsShortestPath, "dfsShortestPath");

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
