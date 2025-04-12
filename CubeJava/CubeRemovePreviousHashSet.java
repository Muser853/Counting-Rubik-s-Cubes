import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class CubeRemovePreviousHashSet {
        public static Set<List<TRFaces>> all = new HashSet<>(Arrays.asList(TRFaces.ThreeFacesArray)), 
                n = new HashSet<>(all);
    public static void main(String[] args) {
        for (int step = 0; all.removeAll(n); n = new HashSet<>(all)) {
            for (List<TRFaces> cube : n) {
                for (int j = 0; j < 6; j++) {
                    all.add(TRFaces.rotate(TRFaces.faceRotationIndices[2 + j % 2],
                        TRFaces.faceRotationIndices[j], cube, TRFaces.rotation[j]
                    ));
                }
            }
            System.out.printf("previousLittleAll: %d, all - previousLittleAll: %d, step: %d\n", n.size(), all.size(), ++step);
        }
        System.out.printf("all: %d", all.size());
    }
}
/*previousLittleAll: 1, all - previousLittleAll: 6, step: 1
previousLittleAll: 6, all - previousLittleAll: 28, step: 2
previousLittleAll: 28, all - previousLittleAll: 126, step: 3
previousLittleAll: 126, all - previousLittleAll: 562, step: 4
previousLittleAll: 562, all - previousLittleAll: 2502, step: 5
previousLittleAll: 2502, all - previousLittleAll: 11108, step: 6
previousLittleAll: 11108, all - previousLittleAll: 49202, step: 7
previousLittleAll: 49202, all - previousLittleAll: 217352, step: 8
previousLittleAll: 217352, all - previousLittleAll: 957452, step: 9
previousLittleAll: 957452, all - previousLittleAll: 4194860, step: 10 */
 /*n size: 1, allStates: 7, step: 1
n size: 6, allStates: 34, step: 2
n size: 28, allStates: 154, step: 3
n size: 126, allStates: 688, step: 4
n size: 562, allStates: 3064, step: 5
n size: 2502, allStates: 13610, step: 6
n size: 11108, allStates: 60310, step: 7
n size: 49202, allStates: 266554, step: 8
n size: 217352, allStates: 1174804, step: 9
n size: 957452, allStates: 5152312, step: 10*/