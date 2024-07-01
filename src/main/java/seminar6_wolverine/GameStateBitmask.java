package seminar6_wolverine;

import java.util.*;

/*
* Es gibt einen Bauern, der einen Wolf, ein Schaf und einen Kohlkopf über einen Fluss bringen will.
* Der Bauer hat ein Boot, das nur ihn und ein weiteres Objekt tragen kann.
* Der Bauer kann das Boot über den Fluss rudern, um die Objekte zu transportieren.
* Wenn der Bauer nicht dabei ist, frisst der Wolf das Schaf und das Schaf frisst den Kohlkopf.
* Wie kann der Bauer alle Objekte sicher über den Fluss bringen?
*
* Implementieren Sie eine Methode, die eine Lösung für das Problem findet.
* Tiefensuche!
*
*
* mit breitensuche oder tiefensuche abbilden mit hilfe von bitmasken
* bitmasken auf enums umwandeln
* */
public class GameStateBitmask {

    public static final byte FARMER_MASK = (byte) 0b1000;
    public static final byte WOLF_MASK = (byte) 0b0100;
    public static final byte SHEEP_MASK = (byte) 0b0010;
    public static final byte CABBAGE_MASK = (byte) 0b0001;

    public final byte POS_LEFT = 0;
    public final byte POS_RIGHT = 1;

    private byte state;

    public GameStateBitmask(){
        // Initialize the state with all figures on the right side
        // Farmer = 1, Wolf = 1, Sheep = 1, Cabbage = 1
        state = (byte) (FARMER_MASK | WOLF_MASK | SHEEP_MASK | CABBAGE_MASK);
    }

    byte getFarmerPos() { // TODO kürzen zu getPos
        return ((state & FARMER_MASK) != 0) ? POS_RIGHT : POS_LEFT;
    }

    byte getWolfPos() {
        return ((state & WOLF_MASK) != 0) ? POS_RIGHT : POS_LEFT;
    }

    byte getSheepPos() {
        return ((state & SHEEP_MASK) != 0) ? POS_RIGHT : POS_LEFT;
    }

    byte getCabbagePos() {
        return ((state & CABBAGE_MASK) != 0) ? POS_RIGHT : POS_LEFT;
    }

    boolean isValid(byte state) {
        byte farmerPos = (byte) ((state & FARMER_MASK) != 0 ? POS_RIGHT : POS_LEFT);
        byte wolfPos = (byte) ((state & WOLF_MASK) != 0 ? POS_RIGHT : POS_LEFT);
        byte sheepPos = (byte) ((state & SHEEP_MASK) != 0 ? POS_RIGHT : POS_LEFT);
        byte cabbagePos = (byte) ((state & CABBAGE_MASK) != 0 ? POS_RIGHT : POS_LEFT);

        // Rules: Wolf can't be alone with the sheep without the farmer
        // Sheep can't be alone with the cabbage without the farmer

        if ((farmerPos != wolfPos && wolfPos == sheepPos) || (farmerPos != sheepPos && sheepPos == cabbagePos)) {
            return false; // Violates the rules
        }

        return true; // State is valid
    }

    boolean isTerminationState(byte state) {
        return state == 0b0000;
    }


    // generiere folgezustände (eine Liste von nachbarn)
    public List<Byte> getPossibleMoves() {
        List<Byte> neighbours = new ArrayList<>();

        byte farmerPos = getFarmerPos();
        byte wolfPos = getWolfPos();
        byte sheepPos = getSheepPos();
        byte cabbagePos = getCabbagePos();

        // Generate all possible moves
        // Farmer moves alone
        if (farmerPos == POS_LEFT) {
            byte newState = (byte) (state | FARMER_MASK); // Toggle farmer position
            if (isValid(newState)) {
                neighbours.add(newState);
            }
        } else {
            byte newState = (byte) (state ^ FARMER_MASK); // Toggle farmer position
            String binaryString = Integer.toBinaryString(newState);
            if (isValid(newState)) {
                neighbours.add(newState);
            }
        }

        // Farmer moves with wolf
        if (farmerPos == wolfPos) {
            byte newState = (byte) (state ^ (FARMER_MASK | WOLF_MASK)); // Toggle farmer and wolf position
            if (isValid(newState)) {
                neighbours.add(newState);
            }
        }

        // Farmer moves with sheep
        if (farmerPos == sheepPos) {
            byte newState = (byte) (state ^ (FARMER_MASK | SHEEP_MASK)); // Toggle farmer and sheep position
            if (isValid(newState)) {
                neighbours.add(newState);
            }
        }

        // Farmer moves with cabbage
        if (farmerPos == cabbagePos) {
            byte newState = (byte) (state ^ (FARMER_MASK | CABBAGE_MASK)); // Toggle farmer and cabbage position
            if (isValid(newState)) {
                neighbours.add(newState);
            }
        }

        return neighbours;
    }

    // Breadth-first search (BFS) (Breitensuche)
    public List<Byte> solveBFS() {
        Queue<Byte> openList = new LinkedList<>();
        List<Byte> closedList = new ArrayList<>();
        Map<Byte, Byte> parent = new HashMap<>(); // For reconstructing the path
        byte initialState = state;
        openList.add(initialState);

        while (!openList.isEmpty()) {
            byte currentState = openList.poll();
            state = currentState;
            if (isTerminationState(currentState)) {
                return reconstructPath(parent, currentState);
            }
            closedList.add(currentState);
            List<Byte> neighbours = getPossibleMoves();
            for (byte neighbour : neighbours) {
                if (!closedList.contains(neighbour)) {
                    openList.add(neighbour);
                    parent.put(neighbour, currentState);
                }
            }
        }

        return null; // No solution found
    }

    // Reconstruct the path from the goal state to the initial state
    private List<Byte> reconstructPath(Map<Byte, Byte> parent, byte goalState) {
        List<Byte> path = new ArrayList<>();
        byte currentState = goalState;
        while (parent.containsKey(currentState)) {
            path.add(0, currentState);
            currentState = parent.get(currentState);
        }
        path.add(0, currentState); // Add the initial state
        return path;
    }

    // Depth-first search (dfs) (tiefensuche)
    public List<Byte> solveDFS() {
        List<Byte> closedList = new ArrayList<>();
        Stack<Byte> openList = new Stack<>();
        openList.add(state);

        if (dfsRecursive(state, closedList, openList)) {
            return openList;
        }

        return null;
    }

    private boolean dfsRecursive(byte currentState, List<Byte> closedList, Stack<Byte> openList) {
        state = currentState;

        if (isTerminationState(state)) {
            return true;
        }

        closedList.add(state);
        List<Byte> neighbours = getPossibleMoves();

        for (byte neighbour : neighbours) {
            if (!closedList.contains(neighbour)) {
                openList.add(neighbour);
                if (dfsRecursive(neighbour, closedList, openList)) {
                    return true;
                }
                openList.pop();
            }
        }

        return false;
    }

    public static void main(String[] args) {
        GameStateBitmask gameState = new GameStateBitmask();
        List<Byte> solutionWithDFS = gameState.solveDFS();
        if (solutionWithDFS != null) {
            System.out.println("Solution with DFS:");
            for (Byte state : solutionWithDFS) {
                // Convert the byte to binary string representation
                String binaryString = String.format("%4s", Integer.toBinaryString(state & 0xFF)).replace(' ', '0');
                System.out.println(binaryString);
            }
        } else {
            System.out.println("No solution found with DFS.");
        }

        System.out.println("\n\n\n");

        gameState = new GameStateBitmask();
        List<Byte> solutionWithBFS = gameState.solveBFS();
        if (solutionWithBFS != null) {
            System.out.println("Solution with BFS:");
            for (Byte state : solutionWithBFS) {
                // Convert the byte to binary string representation
                String binaryString = String.format("%4s", Integer.toBinaryString(state & 0xFF)).replace(' ', '0');
                System.out.println(binaryString);
            }
        } else {
            System.out.println("No solution found with BFS.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateBitmask gameState = (GameStateBitmask) o;
        return state == gameState.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    @Override
    public GameStateBitmask clone() {
        GameStateBitmask clone = new GameStateBitmask();
        clone.state = state;
        return clone;
    }
}
