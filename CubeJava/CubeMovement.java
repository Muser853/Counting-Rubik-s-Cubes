import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
public class CubeMovement {
    public static int[] u(int[] a) {
        int i = 8 - a[4];
        int j = 8 - a[0];
        int m = 8 - a[5];
        int n = 8 - a[1];
        return new int[]{i, j, a[2], a[3], m, n, a[6], a[7]};
    }
    public static int[] n(int[] a) {
        int i = 8 - a[1];
        int j = 8 - a[5];
        int m = 8 - a[0];
        int n = 8 - a[4];
        return new int[]{i, j, a[2], a[3], m, n, a[6], a[7]};
    }
    public static int[] b(int[] a) {
        int i = 0, h = 0, k = 0, j = 0;

        if (a[2] > 8) i = a[2] - 8;
        else if (a[2] > 0) i = a[2] + 8;

        if (a[3] > 8) k = a[3] - 8;
        else if (a[3] > 0) k = a[3] + 8;

        if (a[1] > 8) j = a[1] - 8;
        else if (a[1] > 0) j = a[1] + 8;

        if (a[0] > 8) h = a[0] - 8;
        else if (a[0] > 0) h = a[0] + 8;
        return new int[]{i, h, k, j, a[4], a[5], a[6], a[7]};
    }
    public static int[] d(int[] a) {
        int i = 0, h = 0, k = 0, j = 0;

        if (a[2] > 8) i = a[2] - 8;
        else if (a[2] > 0) i = a[2] + 8;

        if (a[3] > 8) k = a[3] - 8;
        else if (a[3] > 0) k = a[3] + 8;

        if (a[1] > 8) j = a[1] - 8;
        else if (a[1] > 0) j = a[1] + 8;

        if (a[0] > 8) h = a[0] - 8;
        else if (a[0] > 0) h = a[0] + 8;
        return new int[]{j, k, h, i, a[4], a[5], a[6], a[7]};
    }

    public static int[] l(int[] a) {
        return new int[]{a[0], -a[5], a[2], -a[1], a[4], -a[7], a[6], -a[3]};
    }

    public static int[] j(int[] a) {
        return new int[]{a[0], -a[3], a[2], -a[7], a[4], -a[1], a[6], -a[5]};
    }
    public static final List<Function<int[], int[]>> moves = Arrays.asList(
        CubeMovement::u, CubeMovement::n, CubeMovement::b, CubeMovement::d, CubeMovement::l, CubeMovement::j
    );
    public static int[] go(int[] cube, List<int[]> visited, int length, int count) {
        List<int[]> a = new ArrayList<>();
        for (Function<int[], int[]> move : moves) {
            boolean found = false;
            for (int i = 0; i < length; i++) {
                if (Arrays.equals(move.apply(cube), visited.get(i))) {
                    found = true;
                    break;
                }
            }
            if (!found)
                a.add(move.apply(cube));
        }
        length = visited.size();
        visited.addAll(a);

        // Breadth-first search
        if (visited.size() == length) return new int[]{visited.size(), count};
        
        for (int i = length; i < visited.size(); i++) go(visited.get(i), visited, length, count + 1);
        return new int[]{visited.size(), count};
    }

    public static void main(String[] args) {
        int[] cube = {1, 2, 3, 4, 5, 6, 7, 8};
        go(cube, new ArrayList<>(List.of(cube)), 1, 0);
    }
}