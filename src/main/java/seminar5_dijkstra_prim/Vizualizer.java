package seminar5_dijkstra_prim;

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
                    edge.getWeight() + " from " + edge.getVertex1().getName() + " to " + edge.getVertex2().getName(),
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

    public static void visualizeShortestPath(Graph graph, List<Edge> shortestPath, String nameOfGraph) {
        System.setProperty("org.graphstream.ui", "swing");
        org.graphstream.graph.Graph visualGraph = new SingleGraph(nameOfGraph);
        visualGraph.addAttribute("ui.title", nameOfGraph);

        // Add nodes (vertices) to the visual graph
        for (Vertex vertex : graph.getVertexes()) {
            visualGraph.addNode(vertex.getName());
        }

        // Add edges to the visual graph
        for (Edge edge : graph.getEdges()) {
            visualGraph.addEdge(
                    edge.getWeight() + " from " + edge.getVertex1().getName() + " to " + edge.getVertex2().getName(),
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
            if(shortestPath.contains(edge)){
                org.graphstream.graph.Edge visualEdge = visualGraph.getEdge(edge.getWeight() + " from " + edge.getVertex1().getName() + " to " + edge.getVertex2().getName());
                if (visualEdge != null) {
                    visualEdge.setAttribute("ui.style", "fill-color: red; size: 2px; text-size: 18;");
                }
            }
        }

        Viewer viewer = visualGraph.display();
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }
}
