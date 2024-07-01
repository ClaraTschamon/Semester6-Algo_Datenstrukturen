package seminar4_graphs;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.List;

public class Vizualizer {
    public static void visualize(Graph graph) {
        System.setProperty("org.graphstream.ui", "swing");
        org.graphstream.graph.Graph visualGraph = new SingleGraph("Graph");

        // Add nodes (vertexes) to the visual graph
        for (Vertex vertex : graph.getVertexes()) {
            visualGraph.addNode(vertex.getName());
        }

        // Add edges to the visual graph
        for (Edge edge : graph.getEdges()) {
            visualGraph.addEdge(
                    String.valueOf(edge.getWeight()),
                    edge.getVertex1().getName(),
                    edge.getVertex2().getName(),
                    graph.isDirected()
            ).setAttribute("weight", edge.getWeight());
        }

        for (Node node : visualGraph) {
            node.setAttribute("ui.label", node.getId());
            node.setAttribute("ui.style", "text-size: 18;");
        }

        for (org.graphstream.graph.Edge edge : visualGraph.getEdgeSet()) {
            edge.setAttribute("ui.label", edge.getId());
            edge.setAttribute("ui.style", "text-size: 18;");
        }

        Viewer viewer = visualGraph.display();
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
    }

    public static void visualizeShortestPath(Graph graph, List<Vertex> shortestPath, String nameOfGraph) {
        System.setProperty("org.graphstream.ui", "swing");
        org.graphstream.graph.Graph visualGraph = new SingleGraph(nameOfGraph);

        // Add nodes (vertices) to the visual graph
        for (Vertex vertex : graph.getVertexes()) {
            visualGraph.addNode(vertex.getName());
        }

        // Add edges to the visual graph
        for (Edge edge : graph.getEdges()) {
            visualGraph.addEdge(
                    String.valueOf(edge.getWeight()),
                    edge.getVertex1().getName(),
                    edge.getVertex2().getName(),
                    graph.isDirected()
            ).setAttribute("weight", edge.getWeight());
        }

        // Set labels for nodes
        for (Node node : visualGraph) {
            node.setAttribute("ui.label", node.getId());
            node.setAttribute("ui.style", "text-size: 18;");
        }

        // Set labels for edges
        for (org.graphstream.graph.Edge edge : visualGraph.getEdgeSet()) {
            edge.setAttribute("ui.label", edge.getId());
            edge.setAttribute("ui.style", "text-size: 18;");
        }

        // Highlight shortest path edges
        for (Edge edge : graph.getEdges()) {
            String edgeId = String.valueOf(edge.getWeight());
            org.graphstream.graph.Edge visualEdge = visualGraph.getEdge(edgeId);
            if (visualEdge != null && shortestPathContainsEdge(shortestPath, edge)) {
                visualEdge.setAttribute("ui.style", "fill-color: red; size: 2px; text-size: 18;");
            }
        }

        // Highlight shortest path nodes
        for (Vertex vertex : graph.getVertexes()) {
            if (shortestPath.contains(vertex)) {
                Node visualNode = visualGraph.getNode(vertex.getName());
                if (visualNode != null) {
                    visualNode.setAttribute("ui.style", "fill-color: blue; size: 30px; text-size: 18;");
                }
            }
        }

        Viewer viewer = visualGraph.display();
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }

    private static boolean shortestPathContainsEdge(List<Vertex> shortestPath, Edge edge) {
        // Check if the shortest path contains the specified edge
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Vertex current = shortestPath.get(i);
            Vertex next = shortestPath.get(i + 1);
            if ((edge.getVertex1() == current && edge.getVertex2() == next) ||
                    (edge.getVertex1() == next && edge.getVertex2() == current)) {
                return true;
            }
        }
        return false;
    }
}
