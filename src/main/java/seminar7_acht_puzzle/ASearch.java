package seminar7_acht_puzzle;

import java.util.*;

import static seminar7_acht_puzzle.AchtPuzzle.printState;
import static seminar7_acht_puzzle.AchtPuzzle.generateSuccessors;
import static seminar7_acht_puzzle.AchtPuzzle.isGoalState;
import static seminar7_acht_puzzle.Heuristics.calculateHeuristic;

public class ASearch {
    static void aStarSearch(int[][] initialState, int heuristic) {
        PriorityQueue<AchtPuzzle.Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        // f(n) = g(n) + h(n) where g(n) is the estimated cost to reach the goal node and h(n) is the heuristic value (distance from the start to the current value)
        Map<String, Integer> fScores = new HashMap<>();
        // g(n) is the estimated cost to reach the goal node
        Map<String, Integer> gScores = new HashMap<>();

        int expandedNodes = 0;

        String initialStateKey = Arrays.deepToString(initialState);
        openList.add(new AchtPuzzle.Node(initialState, calculateHeuristic(initialState, heuristic)));
        gScores.put(initialStateKey, 0);
        fScores.put(initialStateKey, calculateHeuristic(initialState, heuristic));

        while (!openList.isEmpty()) {
            AchtPuzzle.Node current = openList.poll();
            int[][] currentState = current.state;

            if (isGoalState(currentState)) {
                System.out.println("Goal State Reached!");
                System.out.println("Expanded Nodes: " + expandedNodes);
                printState(currentState);
            }

            List<int[][]> successors = generateSuccessors(currentState);
            expandedNodes++;
            for (int[][] successor : successors) {
                int newGScore = gScores.getOrDefault(initialStateKey, Integer.MAX_VALUE) + 1;
                String successorKey = Arrays.deepToString(successor);

                if (newGScore < gScores.getOrDefault(successorKey, Integer.MAX_VALUE)) {
                    gScores.put(successorKey, newGScore);
                    int fScore = newGScore + calculateHeuristic(successor, heuristic); // f(n) = g(n) + h(n) ... h is the distance from the start to the current node. g is the distance from the current node to the goal
                    fScores.put(successorKey, fScore);
                    openList.add(new AchtPuzzle.Node(successor, fScore));
                }
            }
        }

        System.out.println("Goal State not reachable.");
    }
}
