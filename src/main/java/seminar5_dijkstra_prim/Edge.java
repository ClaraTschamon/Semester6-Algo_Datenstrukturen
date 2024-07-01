package seminar5_dijkstra_prim;

public class Edge {
    private Vertex vertex1;
    private Vertex vertex2;
    private int weight;
    private boolean visited;

    public Edge(Vertex source, Vertex destination, int weight) {
        this.vertex1 = source;
        this.vertex2 = destination;
        this.weight = weight;
        this.visited = false;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
