import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
public class CubeSolver {
    private static final int[][][] cube = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    private static final List<Function<int[][][], int[][][]>> moves = Arrays.asList(
            c -> makeMove(c, "top", true, "un"),
            c -> makeMove(c, "top", false, "un"),
            c -> makeMove(c, "bottom", true, "bd"),
            c -> makeMove(c, "bottom", false, "bd"),
            c -> makeMove(c, "side", true, "lj"),
            c -> makeMove(c, "side", false, "lj")
    );
    private static int[][][] deepCopy(int[][][] original) {
        int[][][] copy = new int[original.length][][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new int[original[i].length][];
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = Arrays.copyOf(original[i][j], original[i][j].length);
            }
        }
        return copy;
    }
    public static IntUnaryOperator getOperation(String moveType) {
        switch (moveType) {
            case "un":
                return x -> {
                    if (x > 8) return -(x - 8);
                    else if (x < 0) return -x + 8;
                    else return x;
                };
            case "bd":
                return x -> {
                    if (x > 8) return x - 8;
                    else return x + 8;
                };
            case "lj":
                return x -> (x > 0) ? -x : Math.abs(x);
            default:
                throw new IllegalArgumentException("Unknown move type: " + moveType);
        }
    }
    private static String cubeToTuple(int[][][] cube) {
        StringBuilder sb = new StringBuilder();
        for (int[][] face : cube) for (int[] row : face) sb.append(Arrays.toString(row));
        return sb.toString();
    }
    public static void main(String[] args) {
        HashSet<String> visited = new HashSet<>();
        ArrayDeque<int[][][]> toVisit = new ArrayDeque<>();
        toVisit.add(deepCopy(cube));
        int step = 0;

        while (!toVisit.isEmpty()) {
            int[][][] currentCube = toVisit.poll();
            String currentState = cubeToTuple(currentCube);
            if (visited.contains(currentState)) continue;
            visited.add(currentState);

            for (Function<int[][][], int[][][]> move : moves) {
                int[][][] newCube = deepCopy(currentCube);
                toVisit.add(move.apply(newCube));
            }
            step++;
        }
        System.out.printf("visited states: %d, states to visit: %d, step: %d\n", visited.size(), toVisit.size(), step);
    }
    private static int[][][] makeMove(int[][][] cube, String faceSelector, boolean clockwise, String moveType) {
        int[][] face;
        if (faceSelector.equals("top")) {
            face = new int[2][2];
            for (int i = 0; i < 2; i++) System.arraycopy(cube[i][0], 0, face[i], 0, 2);
        } else if (faceSelector.equals("bottom")) {
            face = cube[1];
        } else { // side
            face = new int[2][2];
            for (int i = 0; i < 2; i++) for (int j = 0; j < 2; j++) face[i][j] = cube[i][j][1];
        }
        int n = face.length;

        int[][] rotated = new int[n][n];
        if (clockwise) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rotated[j][n - 1 - i] = face[i][j];
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rotated[n - 1 - j][i] = face[i][j];
                }
            }
        }
        IntUnaryOperator operation = getOperation(moveType);
        int[][] transformed = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transformed[i][j] = operation.applyAsInt(rotated[i][j]);
            }
        }
        if (faceSelector.equals("top")) {
            for (int i = 0; i < 2; i++) System.arraycopy(transformed[i], 0, cube[i][0], 0, 2);
        } else if (faceSelector.equals("bottom")) {
            cube[1] = transformed;
        } else {
            for (int i = 0; i < 2; i++) for (int j = 0; j < 2; j++) cube[i][j][1] = transformed[i][j];
        }
        return cube;
    }
}