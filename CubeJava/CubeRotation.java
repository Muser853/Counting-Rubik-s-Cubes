import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CubeRotation {
    public static int[][][] clock(int[][][] b) {
        int n = b.length;
        for (int i = 0; i < (n - 1) / 2 + 1; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = b[i][j][0];
                b[i][j][0] = b[n - 1 - j][i][0];
                b[n - 1 - j][i][0] = b[n - 1 - i][n - 1 - j][0];
                b[n - 1 - i][n - 1 - j][0] = b[j][n - 1 - i][0];
                b[j][n - 1 - i][0] = temp;
            }
        }
        return b;
    }
    public static int[][][] counterClock(int[][][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < (n - 1) / 2 + 1; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j][0];
                matrix[i][j][0] = matrix[j][n - 1 - i][0];
                matrix[j][n - 1 - i][0] = matrix[n - 1 - i][n - 1 - j][0];
                matrix[n - 1 - i][n - 1 - j][0] = matrix[n - 1 - j][i][0];
                matrix[n - 1 - j][i][0] = temp;
            }
        }
        return matrix;
    }
    public static int[][][] u(int[][][] A) {
        int[][][] B = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                B[i][j][0] = A[i][0][j];
            }
        }
        B = clock(B);
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                B[i][k][0] = 8 - B[i][k][0];
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                A[i][0][j] = B[i][j][0];
            }
        }
        return A;
    }
    public static int[][][] n(int[][][] A) {
        int[][][] B = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                B[i][j][0] = A[i][0][j];
            }
        }
        B = counterClock(B);
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                A[i][0][j] = 8 - B[i][j][0];
            }
        }
        return A;
    }
    public static int[][][] b(int[][][] A) {
        int[][][] B = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                B[0][i][k] = A[0][i][k];
            }
        }
        B = clock(B);
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (B[0][i][k] > 8) B[0][i][k] -= 8;
                else if (B[0][i][k] > 0) B[0][i][k] += 8;
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                A[0][i][k] = B[0][i][k];
            }
        }
        return A;
    }
    public static int[][][] d(int[][][] A) {
        int[][][] B = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                B[0][i][k] = A[0][i][k];
            }
        }
        B = counterClock(B);
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (B[0][i][k] > 8) B[0][i][k] -= 8;
                else if (B[0][i][k] > 0) B[0][i][k] += 8;
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++)
                A[0][i][k] = B[0][i][k];
        }
        return A;
    }
    public static int[][][] l(int[][][] A) {
        int[][][] B = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                B[i][j][1] = A[i][j][1];
            }
        }
        B = clock(B);
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++)
                A[i][j][1] = -B[i][j][1];
        }
        return A;
    }
    public static int[][][] j(int[][][] A) {
        int[][][] B = new int[2][2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                B[i][j][1] = A[i][j][1];
            }
        }
        B = counterClock(B);
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++)
                A[i][j][1] = -B[i][j][1];
        }
        return A;
    }

    
    public static int[][][] deepCopy(int[][][] original) {
        int[][][] copy = new int[original.length][][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new int[original[i].length][];
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = Arrays.copyOf(original[i][j], original[i][j].length);
            }
        }
        return copy;
    }
    public static List<int[][][]> go(int[][][] cube, List<int[][][]> visited, int l) {
        List<Function<int[][][], int[][][]>> moves = new ArrayList<>();
        moves.add(CubeRotation::u);
        moves.add(CubeRotation::n);
        moves.add(CubeRotation::b);
        moves.add(CubeRotation::d);
        moves.add(CubeRotation::l);
        moves.add(CubeRotation::j);

        for (Function<int[][][], int[][][]> move : moves) {
            int count = 0;
            int[][][] copiedCube = deepCopy(cube);
            int[][][] result = move.apply(copiedCube);
            for (int i = 0; i < l; i++) {
                if (java.util.Arrays.deepEquals(result, visited.get(i))) {
                    count = 1;
                    break;
                }
            }
            if (count == 0) visited.add(result);
        }
        if (visited.size() != l) {
            for (int i = l; i < visited.size(); i++) {
                go(deepCopy(visited.get(i)), visited, visited.size());
            }
        }
        return visited;
    }

    public static List<int[][][]> boggle(int[][][] cube) {
        return go(cube, new ArrayList<>() {{ add(cube); }}, 1);
    }
    public static void main(String[] args) {
        int[][][] initialCube = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        System.out.println(boggle(initialCube));
    }
}