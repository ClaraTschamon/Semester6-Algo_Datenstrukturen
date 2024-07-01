package seminar7_acht_puzzle;

import java.util.*;
import seminar7_acht_puzzle.AchtPuzzle.Node;

import static seminar7_acht_puzzle.AchtPuzzle.*;
import static seminar7_acht_puzzle.Heuristics.calculateHeuristic;

public class GreedySearch {

    public static void greedySearch(int[][] initialState, int heuristicChoice) {
        Set<String> closedList = new HashSet<>();
        int expandedNodes = 0;

        // Priority openList to store nodes with lowest f value (Heuristic value) first
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(node -> calculateHeuristic(node.state, heuristicChoice)));

        Node initialNode = new Node(initialState, 0);
        openList.add(initialNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (isGoalState(currentNode.state)) {
                System.out.println("Goal State Reached!");
                System.out.println("Expanded Nodes: " + expandedNodes);
                printState(currentNode.state);
                return;
            }

            closedList.add(Arrays.deepToString(currentNode.state));

            List<int[][]> successors = generateSuccessors(currentNode.state);
            expandedNodes++;
            for (int[][] successor : successors) {
                if (!closedList.contains(Arrays.deepToString(successor))) {
                    Node successorNode = new Node(successor, calculateHeuristic(successor, heuristicChoice));
                    openList.add(successorNode);
                }
            }
        }

        System.out.println("Goal State not reachable.");
    }
}
