package seminar4_graphs;

import java.util.*;

public class Euler {

    /* Satz von Euler
    - Ein zusammenhängender Graph hat einen Euler-Weg genau dann, wenn genau zwei
        Knoten einen ungeraden Grad haben.

        Ein Eulerkreis entsteht wenn jede Kante des Zyklus genau einmal genutzt wird.

        Im Unterschied zum Eulerkreis entspricht beim Eulerweg zwar der Anfangsknoten nicht dem Endknoten,
        doch alle Kanten werden ebenfalls genau einmal durchlaufen.

    - Ein zusammenhägender Graph hat einen Euler-Kreis genau dann, wenn alle Knoten
        einen geraden Grad haben.
    */

    // Bedingungen für Eulerweg:
    // 1. Der Graph muss zusammenhängend sein
    // 2. Der Graph muss ungerichtet sein
    // 3. Der Graph darf 2 oder 0 Knoten mit ungeradem Grad haben

    public boolean isEulerian(Graph g) {
        // 1. der Graph muss zusammenhängend sein
        if (!g.isConnected()){
            return false;
        }

        // 2. der Graph muss ungerichtet sein
        if (g.isDirected()){
            return false;
        }

        // 3. Der Graph muss 2 oder 0 Knoten mit ungeradem Grad haben
        // Count vertices with odd degree
        int oddDegreeCount = 0;
        for (Vertex v : g.getVertexes()) {
            if (v.getDegree() % 2 != 0) {
                oddDegreeCount++;
            }
        }

        // Determine Eulerian conditions
        return oddDegreeCount == 0 || oddDegreeCount == 2;
    }

    public List<Vertex> findEulerianPath(Graph graph) {
        List<Vertex> eulerianPath = new ArrayList<>();
        Vertex startVertex = findStartVertex(graph);

        if (startVertex != null) {
            eulerianPath = graph.findEulerianPath(startVertex);
        }

        return eulerianPath;
    }

    private Vertex findStartVertex(Graph graph) {
        for (Vertex v : graph.getVertexes()) {
            if (v.getDegree() % 2 != 0) {
                return v;
            }
        }
        return graph.getVertexes().get(0); // Return any vertex if no odd-degree vertex found
    }

    public static void main(String[] args) {
        Euler euler = new Euler();
        Graph graph = new Graph(false);

        try {
            graph.loadFromFile("src/main/java/sem4_graphs/HouseOfNikolausGraph.txt");
            System.out.println("Graph is Eulerian: " + euler.isEulerian(graph));

            if(!euler.isEulerian(graph)){
                return;
            }

            List<Vertex> eulerianPath = euler.findEulerianPath(graph);
            System.out.println("Eulerian Path:");
            for (Vertex vertex : eulerianPath) {
                System.out.println(vertex.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
