package seminar8_uniformcost_kruskal.uniformcost;

import java.util.*;

import static seminar8_uniformcost_kruskal.uniformcost.AchtPuzzle.isGoalState;
import static seminar8_uniformcost_kruskal.uniformcost.AchtPuzzle.generateSuccessors;

public class UniformCost {

    static class Node {
        int[][] state;
        int cost;

        Node(int[][] state, int cost) {
            this.state = state;
            this.cost = cost;
        }
    }

    private static int expansions = 0;

    // Uniform-Costist eine Modifikation der Breitensuche,
    // die immer zuerst den Knoten nmit den geringsten Pfadkosten g(n)expandiert.

    static void solveWithUniformCost(int[][] initialState) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Set<String> closedList = new HashSet<>();

        openList.add(new Node(initialState, 0));

        while(!openList.isEmpty()) {
            Node currentNode = openList.poll();
            int[][] currentState = currentNode.state;

            if (isGoalState(currentState)) {
                System.out.println("Goal State Reached!");
                System.out.println("Expanded Nodes: " + expansions);
                AchtPuzzle.printState(currentState);
                return;
            }

            closedList.add(Arrays.deepToString(currentState));

            List<int[][]> successors = generateSuccessors(currentState);
            expansions++;

            for (int[][] successor : successors) {
                if (!closedList.contains(Arrays.deepToString(successor))) {
                    openList.add(new Node(successor, 1)); // For this example, cost is always 1. Usually it would be the cost of the edge
                }
            }
        }

        System.out.println("Goal State not reachable.");
    }
}
