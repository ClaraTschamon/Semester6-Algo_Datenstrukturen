package seminar6_wolverine;

import java.util.*;

public class GameStateObjectOriented {

    public enum Position {
        LEFT, RIGHT
    }

    public enum Object {
        FARMER, WOLF, SHEEP, CABBAGE
    }

    private Map<Object, Position> positions;

    public GameStateObjectOriented() {
        positions = new HashMap<>();
        for (Object obj : Object.values()) {
            positions.put(obj, Position.RIGHT);
        }
    }

    public void setPosition(Object obj, Position pos) {
        positions.put(obj, pos);
    }

    public boolean isValid() {
        Position farmerPos = positions.get(Object.FARMER);
        Position wolfPos = positions.get(Object.WOLF);
        Position sheepPos = positions.get(Object.SHEEP);
        Position cabbagePos = positions.get(Object.CABBAGE);

        // Rules: Wolf can't be alone with the sheep without the farmer
        // Sheep can't be alone with the cabbage without the farmer
        if ((farmerPos != wolfPos && wolfPos == sheepPos) || (farmerPos != sheepPos && sheepPos == cabbagePos)) {
            return false; // Violates the rules
        }

        return true; // State is valid
    }

    public boolean isTerminationState() {
        return positions.values().stream().allMatch(pos -> pos == Position.LEFT);
    }

    public List<GameStateObjectOriented> getPossibleMoves() {
        List<GameStateObjectOriented> neighbours = new ArrayList<>();

        Position farmerPos = positions.get(Object.FARMER);
        Position wolfPos = positions.get(Object.WOLF);
        Position sheepPos = positions.get(Object.SHEEP);
        Position cabbagePos = positions.get(Object.CABBAGE);

        GameStateObjectOriented currentState = this.clone();

        // Generate all possible moves
        // Farmer moves alone
        if (farmerPos == Position.LEFT) {
            GameStateObjectOriented newState = currentState.clone();
            newState.setPosition(Object.FARMER, Position.RIGHT); // Toggle farmer position
            if (newState.isValid()) {
                neighbours.add(newState);
            }
        } else {
            GameStateObjectOriented newState = currentState.clone();
            newState.setPosition(Object.FARMER, Position.LEFT); // Toggle farmer position
            if (newState.isValid()) {
                neighbours.add(newState);
            }
        }

        // Farmer moves with wolf
        if (farmerPos == wolfPos) {
            GameStateObjectOriented newState = currentState.clone();
            newState.setPosition(Object.FARMER, farmerPos == Position.LEFT ? Position.RIGHT : Position.LEFT); // Toggle farmer position
            newState.setPosition(Object.WOLF, wolfPos == Position.LEFT ? Position.RIGHT : Position.LEFT); // Toggle wolf position
            if (newState.isValid()) {
                neighbours.add(newState);
            }
        }

        // Farmer moves with sheep
        if (farmerPos == sheepPos) {
            GameStateObjectOriented newState = currentState.clone();
            newState.setPosition(Object.FARMER, farmerPos == Position.LEFT ? Position.RIGHT : Position.LEFT); // Toggle farmer position
            newState.setPosition(Object.SHEEP, sheepPos == Position.LEFT ? Position.RIGHT : Position.LEFT); // Toggle sheep position
            if (newState.isValid()) {
                neighbours.add(newState);
            }
        }

        // Farmer moves with cabbage
        if (farmerPos == cabbagePos) {
            GameStateObjectOriented newState = currentState.clone();
            newState.setPosition(Object.FARMER, farmerPos == Position.LEFT ? Position.RIGHT : Position.LEFT); // Toggle farmer position
            newState.setPosition(Object.CABBAGE, cabbagePos == Position.LEFT ? Position.RIGHT : Position.LEFT); // Toggle cabbage position
            if (newState.isValid()) {
                neighbours.add(newState);
            }
        }

        return neighbours;
    }

    public List<GameStateObjectOriented> solveBFS() {
        Queue<GameStateObjectOriented> openList = new LinkedList<>();
        List<GameStateObjectOriented> closedList = new ArrayList<>();
        Map<GameStateObjectOriented, GameStateObjectOriented> parent = new HashMap<>();
        GameStateObjectOriented initialState = this.clone();
        openList.add(initialState);

        while (!openList.isEmpty()) {
            GameStateObjectOriented currentState = openList.poll();
            closedList.add(currentState);
            if (currentState.isTerminationState()) {
                return reconstructPath(parent, currentState);
            }
            List<GameStateObjectOriented> neighbours = currentState.getPossibleMoves();
            for (GameStateObjectOriented neighbour : neighbours) {
                if (!closedList.contains(neighbour)) {
                    openList.add(neighbour);
                    parent.put(neighbour, currentState);
                }
            }
        }

        return null; // No solution found
    }

    private List<GameStateObjectOriented> reconstructPath(Map<GameStateObjectOriented, GameStateObjectOriented> parent, GameStateObjectOriented goalState) {
        List<GameStateObjectOriented> path = new ArrayList<>();
        GameStateObjectOriented currentState = goalState;
        while (parent.containsKey(currentState)) {
            path.add(0, currentState);
            currentState = parent.get(currentState);
        }
        path.add(0, currentState); // Add the initial state
        return path;
    }

    public List<GameStateObjectOriented> solveDFS() {
        List<GameStateObjectOriented> closedList = new ArrayList<>();
        Stack<GameStateObjectOriented> openList = new Stack<>();
        openList.add(this);

        if (dfsRecursive(closedList, openList)) {
            return closedList;
        }

        return null;
    }

    private boolean dfsRecursive(List<GameStateObjectOriented> closedList, Stack<GameStateObjectOriented> openList) {
        GameStateObjectOriented currentState = openList.pop();
        closedList.add(currentState);
        if (currentState.isTerminationState()) {
            return true;
        }

        List<GameStateObjectOriented> neighbours = currentState.getPossibleMoves();

        for (GameStateObjectOriented neighbour : neighbours) {
            openList.add(neighbour);
            if (!closedList.contains(neighbour)) { // Prevent cycles
                if (dfsRecursive(closedList, openList)) {
                    return true;
                }
            }
        }

        openList.remove(currentState); // Backtrack if no path found
        return false;
    }

    public static void main(String[] args) {
        GameStateObjectOriented gameState = new GameStateObjectOriented();
        List<GameStateObjectOriented> solutionWithDFS = gameState.solveDFS();
        if (solutionWithDFS != null) {
            //Collections.reverse(solutionWithDFSList);
            System.out.println("Solution with DFS:");
            for (GameStateObjectOriented state : solutionWithDFS) {
                System.out.println(state);
            }
        } else {
            System.out.println("No solution found with DFS.");
        }

        System.out.println("---------------------------------------------");

        if (solutionWithDFS != null) {
            System.out.println("Solution with DFS:");
            for (GameStateObjectOriented state : solutionWithDFS) {
                System.out.println(state);
            }
        } else {
            System.out.println("No solution found with DFS.");
        }

        System.out.println("\n");

        gameState = new GameStateObjectOriented();
        List<GameStateObjectOriented> solutionWithBFS = gameState.solveBFS();
        if (solutionWithBFS != null) {
            System.out.println("Solution with BFS:");
            for (GameStateObjectOriented state : solutionWithBFS) {
                System.out.println(state);
            }
        } else {
            System.out.println("No solution found with BFS.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Object, Position> entry : positions.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public GameStateObjectOriented clone(){
        GameStateObjectOriented newState = new GameStateObjectOriented();
        newState.positions = new HashMap<>(this.positions);
        return newState;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateObjectOriented that = (GameStateObjectOriented) o;
        return Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
    }
}
