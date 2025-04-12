import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
public class TRFaces {
    int w, x, y, z;
    public TRFaces(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public static final TRFaces[] rotation = {
        new TRFaces(1, 0, 0),
        new TRFaces(1, 0, 0),
        new TRFaces(0, 1, 0),
        new TRFaces(0, 1, 0),
        new TRFaces(0, 0, 1),
        new TRFaces(0, 0, 1)
    };
    public static final List<TRFaces> ThreeFacesArray = Arrays.asList(
        new TRFaces(1, 0, 0),
        new TRFaces(2, 0, 0),
        new TRFaces(3, 0, 0),
        new TRFaces(4, 0, 0),
        new TRFaces(5, 0, 0),
        new TRFaces(6, 0, 0),
        new TRFaces(7, 0, 0),
        new TRFaces(8, 0, 0)
    );
    public static final int[][] faceRotationIndices = new int[][] {
        {4, 0, 5, 1}, // up
        {1, 5, 0, 4}, // down
        {2, 0, 3, 1}, // back
        {1, 3, 0, 2}, // front
        {1, 5, 7, 3}, // left
        {3, 7, 5, 1}  // right
    };
    public static List<TRFaces> rotate(int [] index, int[] indices, List<TRFaces> cube, TRFaces rotation) {
        List<TRFaces> result = new ArrayList<>(cube);
        for (int i = 0; i < 4; i++)result.set(indices[index[i]], cube.get(indices[i]).multiply(rotation));
        return result;
    }
    public TRFaces multiply(TRFaces other) {
        return new TRFaces(
            this.y * other.z + this.z * other.y + this.x * other.x, 
            this.z * other.x + this.x * other.z + this.y * other.y,
            this.x * other.y + this.y * other.x + this.z * other.z
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        TRFaces that = (TRFaces) obj;
        return this.x == that.x && this.y == that.y && this.z == that.z;
    }
    @Override
    public int hashCode() { return Arrays.hashCode(new int[]{this.x, this.y, this.z}); }

    @Override
    public String toString() {return String.format("TRFaces(%d, %d, %d)", x, y, z);}
}