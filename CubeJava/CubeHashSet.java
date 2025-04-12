import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class CubeHashSet {
    public static Set<List<TRFaces>> all = Collections.synchronizedSet(new HashSet<>(Arrays.asList(TRFaces.ThreeFacesArray)));
    public static int step = 0;
    public static void main(String[] args) {
        for (int size = 0; size != all.size(); size = all.size()) {
            for (List<TRFaces> cube : all) {
                for (int j = 0; j < 6; j++) {
                    all.add(TRFaces.rotate(TRFaces.faceRotationIndices[2 + j % 2],
                        TRFaces.faceRotationIndices[j], cube, TRFaces.rotation[j]
                    ));
                }
            }
            System.out.printf("all: %d, step: %d\n", size, ++step);
        }
        System.out.printf("all: %d", all.size());
    }
}