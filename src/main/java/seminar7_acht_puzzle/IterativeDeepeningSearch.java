package seminar7_acht_puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static seminar7_acht_puzzle.AchtPuzzle.*;

public class IterativeDeepeningSearch {

    private static final int MAX_DEPTH = 60; // to avoid infinite loop
    private static int expandedNodes = 0;

    static void iterativeDeepeningSearch(int[][] initialState) {
        for (int depth = 0; depth <= MAX_DEPTH; depth++) {
            System.out.println("Depth: " + depth);
            Set<String> visited = new HashSet<>();
            if (depthLimitedSearch(initialState, depth, visited)) {
                return;
            }
        }
        System.out.println("Goal State not reachable within depth limit.");
    }

    // Since we increment the limit depth by 1, we know that the first path DLDFS finds is the shortest
    // among all that end in a target node: had it been otherwise, DLDFS would’ve found another path at a
    // shallower depth.

    // mit jedem Schritt wird die Tiefe erhöht. Alle Knoten, die in der vorherigen Iteration besucht wurden, werden
    // in der nächsten Iteration nicht mehr besucht. Dank der visited Liste.
    private static boolean depthLimitedSearch(int[][] state, int depth, Set<String> visited) {
        if (depth == 0 && isGoalState(state)) {
            System.out.println("Goal State Reached!");
            System.out.println("Expanded Nodes: " + expandedNodes);
            printState(state);
            return true;
        }

        if (depth > 0) {
            visited.add(Arrays.deepToString(state));
            List<int[][]> successors = generateSuccessors(state);
            expandedNodes++;
            for (int[][] successor : successors) {
                if (!visited.contains(Arrays.deepToString(successor))) {
                    if (depthLimitedSearch(successor, depth - 1, visited)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
