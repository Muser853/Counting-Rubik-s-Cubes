import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
//for manipulating a 2x2*2 cube and finding unique states through 6 kinds of rotations.
public class CubeManipulator {
    public static int[][] rotateMatrix(int[][] matrix, boolean clockwise) {
        int n = matrix.length;
        int[][] rotatedMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                rotatedMatrix[i][j] = clockwise ? matrix[n - 1 - j][i] : matrix[i][j];
        }
        return clockwise ? reverseMatrix(rotatedMatrix) : rotatedMatrix;
    }
    private static int[][] reverseMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] reversedMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                reversedMatrix[i][j] = matrix[n - 1 - i][j];
            }
        }
        return reversedMatrix;
    }
    public static int[][] transformValues(int[][] matrix, IntUnaryOperator operation) {
        int n = matrix.length;
        int[][] transformedMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                transformedMatrix[i][j] = operation.applyAsInt(matrix[i][j]);
        }
        return transformedMatrix;
    }
    public static IntUnaryOperator getOperation(String moveType) {
        // Precomputed lookup tables
        HashMap<Integer, Integer> unLookup = new HashMap<>(),
        bdLookup = new HashMap<>(), ljLookup = new HashMap<>();

        for (int x = -8; x < 17; x++) {
            unLookup.put(x, (x > 8 ? -(x - 8) : (x < 0 ? -x + 8 : x)));
            bdLookup.put(x, (x > 8 ? x - 8 : (x <= 8 ? x + 8 : x)));
            ljLookup.put(x, (x > 0 ? -x : Math.abs(x)));
        }
        HashMap<String, IntUnaryOperator> operations = new HashMap<>();
        operations.put("un", x -> unLookup.get(x));
        operations.put("bd", x -> bdLookup.get(x));
        operations.put("lj", x -> ljLookup.get(x));

        return operations.get(moveType);
    }

    public static int[][][] makeMove(int[][][] c, String faceSelector, boolean clockwise, String moveType) {
        int[][] face;
        if (faceSelector.equals("top")) {
            face = new int[2][2];
            for (int i = 0; i < 2; i++) {
                System.arraycopy(c[0][i], 0, face[i], 0, 2); // top c[0]
            }
        } else if (faceSelector.equals("bottom")) {
            face = new int[2][2];
            for (int i = 0; i < 2; i++) {
                System.arraycopy(c[1][i], 0, face[i], 0, 2); // copy bottom c[1]
            }
        } else {  // side
            face = new int[2][2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    face[i][j] = c[i][j][1]; // coloumn
                }
            }
        }
        face = rotateMatrix(face, clockwise);
        face = transformValues(face, getOperation(moveType));
        
        if (faceSelector.equals("top")) {
            for (int i = 0; i < 2; i++) {
                System.arraycopy(face[i], 0, c[0][i], 0, 2); // top
            }
        } else if (faceSelector.equals("bottom")) {
            for (int i = 0; i < 2; i++) {
                System.arraycopy(face[i], 0, c[1][i], 0, 2); // bottom c[1]
            }
        } else {  // side
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    c[i][j][1] = face[i][j];
                }
            }
        }
        return c;
    }
    // Partial application of moves
    //type mismatch: can not copy object array[] into int[]
    public static Function<int[][][], int[][][]> u = c -> makeMove(c, "top", true, "un"),
    n = c -> makeMove(c, "top", false, "un"), 
    b = c -> makeMove(c, "bottom", true, "bd"), d = c -> makeMove(c, "bottom", false, "bd"), 
    l = c -> makeMove(c, "side", true, "lj"), j = c -> makeMove(c, "side", false, "lj");

    public static List<Function<int[][][], int[][][]>> moves = Arrays.asList(u, n, b, d, l, j);

    public static int cubeToTuple(int[][][] cube) {
        return Arrays.deepHashCode(cube);
    }
    private static HashSet<Integer> visited = new HashSet<>();

    public static int boggle(int[][][] cube) {
        visited.clear();
        Stack<int[][][]> toVisit = new Stack<>();
        toVisit.push(cube);

        while (!toVisit.isEmpty()) {
            int currentState = cubeToTuple(toVisit.pop());
            if (visited.contains(currentState)) continue;
            visited.add(currentState);
            
            // Create new states and add to visit queue
            for (Function<int[][][], int[][][]> move : moves) {
                int[][][] newCube = new int[cube.length][][];
                for (int i = 0; i < cube.length; i++) {
                    newCube[i] = new int[2][2]; 
                    for (int j = 0; j < 2; j++) {
                        newCube[i][j] = Arrays.copyOf(cube[i][j], 2); // copy by each row
                    }
                }
                toVisit.push(move.apply(newCube));
            }
        }
        return visited.size();
    }
    public static void main(String[] args) {
        int[][][] initialCube = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        int totalStates = boggle(initialCube);//Exception in thread "main" java.lang.ArrayStoreException:
        System.out.printf("Total unique states: %d%n", totalStates);
    }
}