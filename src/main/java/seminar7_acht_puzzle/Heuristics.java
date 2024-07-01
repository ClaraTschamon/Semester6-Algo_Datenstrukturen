package seminar7_acht_puzzle;

import static seminar7_acht_puzzle.AchtPuzzle.goalState;

public class Heuristics {

    // Heuristic 1: Number of misplaced tiles
    public static int calculateH1(int[][] state) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != goalState[i][j] && state[i][j] != 0) {
                    h++;
                }
            }
        }
        return h;
    }

    // Heuristic 2: Manhattan distance of each tile to its correct position
    public static int calculateH2(int[][] state) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != 0) {
                    int targetRow = (state[i][j] - 1) / state.length;
                    int targetCol = (state[i][j] - 1) % state.length;
                    h += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return h;
    }

    // Heuristic 3: Number of tiles which are at the right position
    public static int calculateH3(int[][] state) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == goalState[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    public static int calculateHeuristic(int[][] state, int heuristic) {
        if (heuristic == 1) {
            return calculateH1(state);
        } else if (heuristic == 2) {
            return calculateH2(state);
        } else if (heuristic == 3) {
            return calculateH3(state);
        } else {
            throw new IllegalArgumentException("Invalid heuristic.");
        }
    }
}
