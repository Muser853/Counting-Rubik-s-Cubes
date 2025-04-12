import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.Deque;
import java.util.LinkedList;

public class CubeMan {
    private static final int[][][] cube = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    public static int[][] clock(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i <= (n - 1) / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
        return matrix;
    }
    public static int[][] counterclock(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i <= (n - 1) / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = temp;
            }
        }
        return matrix;
    }
    public static int[][][] rotateFace(int[][][] A, int faceIndex, boolean clockwise) {
        int[][] B = new int[2][2];
        for (int i = 0; i < 2; i++) for (int j = 0; j < 2; j++) B[i][j] = A[i][faceIndex][j];
        B = clockwise ? clock(B) : counterclock(B);
        for (int i = 0; i < 2; i++) for (int k = 0; k < 2; k++) A[i][faceIndex][k] = B[i][k];
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

    public static int[][][] u(int[][][] A) {
        int[][][] newA = deepCopy(A);
        newA = rotateFace(newA, 0, true);
        for (int i = 0; i < 2; i++) for (int k = 0; k < 2; k++) newA[i][0][k] = 8 - newA[i][0][k];
        return newA;
    }

    public static int[][][] n(int[][][] A) {
        int[][][] newA = deepCopy(A);
        newA = rotateFace(newA, 0, false);
        for (int i = 0; i < 2; i++) for (int k = 0; k < 2; k++) newA[i][0][k] = 8 - newA[i][0][k];
        return newA;
    }

    public static int[][][] b(int[][][] A) {
        int[][][] newA = deepCopy(A);
        newA = rotateFace(newA, 0, true);
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (newA[i][0][k] > 8) newA[i][0][k] -= 8;
                else if (newA[i][0][k] > 0) newA[i][0][k] += 8;
            }
        }
        return newA;
    }
    public static int[][][] d(int[][][] A) {
        int[][][] newA = deepCopy(A);
        newA = rotateFace(newA, 0, false);
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (newA[i][0][k] > 8) newA[i][0][k] -= 8;
                else if (newA[i][0][k] > 0) newA[i][0][k] += 8;
            }
        }
        return newA;
    }
    public static int[][][] l(int[][][] A) {
        int[][][] newA = deepCopy(A);
        newA = rotateFace(newA, 1, true);
        for (int i = 0; i < 2; i++) for (int k = 0; k < 2; k++) newA[i][k][1] = -newA[i][k][1];
        return newA;
    }
    public static int[][][] j(int[][][] A) {
        int[][][] newA = deepCopy(A);
        newA = rotateFace(newA, 1, false);
        for (int i = 0; i < 2; i++) for (int k = 0; k < 2; k++) newA[i][k][1] = -newA[i][k][1];
        return newA;
    }
    private static class State {
        public int[][][] cube;
        public List<int[][][]> visited;
        public int length;
        public int count;
        public State(int[][][] cube, List<int[][][]> visited, int length, int count) {
            this.cube = cube;
            this.visited = visited;
            this.length = length;
            this.count = count;
        }
    }
    public static int[] go(int[][][] cube, List<int[][][]> visited, int length, int count) { // 修改go方法为迭代方式
        Deque<State> stack = new LinkedList<>();
        stack.push(new State(cube, visited, length, count));
        int[] result = new int[]{0, 0};
        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            if (currentState.length > result[0]) {
                result[0] = currentState.length;
                result[1] = currentState.count;
            } else if (currentState.length == result[0]) {
                result[1] += currentState.count;
            }
            List<Supplier<int[][][]>> moves = Arrays.asList(
                () -> {
                    int[][][] cloned = deepCopy(currentState.cube);
                    return u(cloned);
                },
                () -> {
                    int[][][] cloned = deepCopy(currentState.cube);
                    return n(cloned);
                },
                () -> {
                    int[][][] cloned = deepCopy(currentState.cube);
                    return b(cloned);
                },
                () -> {
                    int[][][] cloned = deepCopy(currentState.cube);
                    return d(cloned);
                },
                () -> {
                    int[][][] cloned = deepCopy(currentState.cube);
                    return l(cloned);
                },
                () -> {
                    int[][][] cloned = deepCopy(currentState.cube);
                    return j(cloned);
                }
            );
            for (Supplier<int[][][]> move : moves) {
                int[][][] newCube = move.get();
                boolean isVisited = false;
                for (int i = 0; i < currentState.visited.size(); i++) {
                    if (Arrays.deepEquals(newCube, currentState.visited.get(i))) {
                        isVisited = true;
                        break;
                    }
                }
                if (!isVisited) {
                    List<int[][][]> newVisited = new ArrayList<>(currentState.visited);
                    newVisited.add(newCube);
                    int newLength = newVisited.size();
                    int newCount = currentState.count + 1;
                    stack.push(new State(newCube, newVisited, newLength, newCount));
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        List<int[][][]> visited = new ArrayList<>();
        visited.add(deepCopy(cube));
        int [] result = go(cube, visited, 1, 0);
        System.out.println("Length: " + result[0]);
        System.out.println("Count: " + result[1]);
    }
}