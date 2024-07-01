package seminar7_acht_puzzle;

import java.util.*;

import static seminar7_acht_puzzle.ASearch.aStarSearch;
import static seminar7_acht_puzzle.IterativeDeepeningSearch.iterativeDeepeningSearch;

public class AchtPuzzle {
    static final int[][] goalState = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

    public static void main(String[] args) {
        int[][] initialState = {{8, 0, 6}, {5, 4, 7}, {2, 3, 1}};

        System.out.println("Initial State:");
        printState(initialState);

        System.out.println("\nSolving using Iterative Deepening Search:");
        iterativeDeepeningSearch(initialState);

        System.out.println("\nSolving using A* Search with h1:");
        aStarSearch(initialState, 1);

        System.out.println("\nSolving using A* Search with h2:");
        aStarSearch(initialState, 2);

        System.out.println("\nSolving using A* Search with h3:");
        aStarSearch(initialState, 3);

        System.out.println("\nSolving using Greedy Search with h1:");
        GreedySearch.greedySearch(initialState, 1);

        System.out.println("\nSolving using Greedy Search with h2:");
        GreedySearch.greedySearch(initialState, 2);

        System.out.println("\nSolving using Greedy Search with h3:");
        GreedySearch.greedySearch(initialState, 3);
    }

    static class Node {
        int[][] state;
        int f;

        Node(int[][] state, int f) {
            this.state = state;
            this.f = f;
        }
    }

    static void printState(int[][] state) {
        for (int[] row : state) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static int[][] copyState(int[][] state) {
        int[][] newState = new int[state.length][];
        for (int i = 0; i < state.length; i++) {
            newState[i] = Arrays.copyOf(state[i], state[i].length);
        }
        return newState;
    }

    public static boolean isGoalState(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != goalState[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Generate successors by swapping the zero tile with its neighbors
    static List<int[][]> generateSuccessors(int[][] state) {
        List<int[][]> successors = new ArrayList<>();
        int zeroRow = -1;
        int zeroCol = -1;

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        // Generate successors by swapping the zero tile with its neighbors
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = zeroRow + dRow[i];
            int newCol = zeroCol + dCol[i];

            if (newRow >= 0 && newRow < state.length && newCol >= 0 && newCol < state[0].length) {
                int[][] successor = copyState(state);
                successor[zeroRow][zeroCol] = state[newRow][newCol];
                successor[newRow][newCol] = 0;
                successors.add(successor);
            }
        }

        return successors;
    }
}
