import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

public class Cube {
    private int[][][] state;
    private static final int[][][] initialCube = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    // Constants for move types
    private static final HashSet<String> UpDown_MOVES = new HashSet<>(Arrays.asList("u", "n")),
                                        BackFront_MOVES = new HashSet<>(Arrays.asList("b", "d"));
    private static final String[] ALL_MOVES = {"u", "n", "b", "d", "l", "j"};

    public Cube(int[][][] initialState) {
        this.state = initialState;
    }
    // Rotate a 2x2 matrix clockwise / counterclockwise.
    private int[][] rotateMatrix(int[][] matrix, boolean clockwise) {
        if (clockwise) {
            return new int[][]{
                {matrix[1][0], matrix[0][0]},
                {matrix[1][1], matrix[0][1]}
            };
        }
        return new int[][]{
                {matrix[0][1], matrix[1][1]},
                {matrix[0][0], matrix[1][0]}
        };
    }
    // Transform value based on move type.
    private int transformValue(int value, String moveType) {
        if (value == 0) return 0;
        if (UpDown_MOVES.contains(moveType)) return Math.min(value, 8);
        if (BackFront_MOVES.contains(moveType)) return (value + 8) % 16 > 0 ? (value + 8) % 16 : value;
        return value < 9 ? -value : value;
    }
    private void applyMove(String moveType) {
        int[][][] newCube = Arrays.copyOf(state, state.length);
        for (int i = 0; i < state.length; i++) {
            newCube[i] = Arrays.copyOf(state[i], state[i].length);
            for (int j = 0; j < state[i].length; j++) newCube[i][j] = Arrays.copyOf(state[i][j], state[i][j].length);
        }
        int[][] face; // Determine face slice based on move type
        if (UpDown_MOVES.contains(moveType)) face = new int[][]{newCube[0][0], newCube[0][1]};
        else if (BackFront_MOVES.contains(moveType)) face = new int[][]{newCube[1][0], newCube[1][1]};
        else face = new int[][]{newCube[0][1], newCube[1][1]};

        int[][] rotated = rotateMatrix(face, true);
        for (int i = 0; i < rotated.length; i++) {
            for (int j = 0; j < rotated[i].length; j++) {
                if (UpDown_MOVES.contains(moveType)) {
                    if (i < newCube[0].length && j < newCube[0][i].length) {
                        newCube[0][i][j] = transformValue(rotated[i][j], moveType);
                    }
                } else if (BackFront_MOVES.contains(moveType)) {
                    if (i < newCube[1].length && j < newCube[1][i].length) {
                        newCube[1][i][j] = transformValue(rotated[i][j], moveType);
                    }
                } else {
                    if (i < newCube.length && j < newCube[i][1].length) {
                        newCube[i][1][j] = transformValue(rotated[i][j], moveType);
                    }
                }
            }
        }
        this.state = newCube;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        Cube cube = (Cube) obj;
        return Arrays.deepEquals(state, cube.state);
    }
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }
    public static void main(String[] args) {
        HashSet<String> visited = new HashSet<>();
        visited.add(Arrays.deepToString(initialCube));
        Queue<int[][][]> queue = new LinkedList<>();
        queue.add(Arrays.copyOf(initialCube, initialCube.length));

        while (!queue.isEmpty()) {
            int[][][] current = queue.poll();
            Cube tempCube = new Cube(current);
            
            for (String move : ALL_MOVES) {
                tempCube.applyMove(move);
                String stateTuple = Arrays.deepToString(tempCube.state);

                if (!visited.contains(stateTuple)) {
                    visited.add(stateTuple);
                    queue.add(Arrays.copyOf(tempCube.state, tempCube.state.length));
                }
            }
        }
        System.out.println("Total unique states: " + visited.size());
    }
}