import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CubeLinkedHashSet {
    public static int step = 0, size = 1;
    public static volatile Set<List<TRFaces>> all = Collections.synchronizedSet(new LinkedHashSet<>(Arrays.asList(TRFaces.ThreeFacesArray)));
    public static void main(String[] args) {
        Iterator<List<TRFaces>> n = all.iterator();
        synchronized (n) {
            do {
                for (int i = 0; i < size; i++) {
                    List<TRFaces> cube = n.next();
                    for (int j = 0; j < 6; j++) {
                        all.add(TRFaces.rotate(TRFaces.faceRotationIndices[2 + j % 2], 
                            TRFaces.faceRotationIndices[j], cube, TRFaces.rotation[j]));
                    }
                }
                size = all.size() - size;
                System.out.printf("addStates: %d, step: %d\n", size, ++step);
            } while (size > 0);
        }
        System.out.printf("allSize: %d", all.size());
    }
}