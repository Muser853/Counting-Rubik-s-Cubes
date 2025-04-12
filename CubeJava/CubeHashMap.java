import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CubeHashMap {
    public static Map<List<TRFaces>, Integer> states = new HashMap<>();
    public static List<List<TRFaces>> currentLevel = new ArrayList<>();
    public static List<TRFaces> entr = new ArrayList<>();
    public static int size = 1;

    public static void main(String[] args) {
        states.put(TRFaces.ThreeFacesArray, 0);
        for (int step = 0; size != 0; System.out.printf("CurrentSize: %d, Step: %d%n", size, ++step)) {
            for (Map.Entry<List<TRFaces>, Integer> entry : states.entrySet()) if (entry.getValue() == step) currentLevel.add(entry.getKey());
            size = currentLevel.size();
            for (int j = 0; j < size; j++) {
                entr = currentLevel.removeLast();
                for (int i = 0; i < 6; i++) states.putIfAbsent(TRFaces.rotate(TRFaces.faceRotationIndices[2 + i % 2], TRFaces.faceRotationIndices[i], entr, TRFaces.rotation[i]), step+1);
            }
        }
        System.out.printf("allSize: %d", states.size());
    }
} /*CurrentSize: 1, Step: 1
CurrentSize: 6, Step: 2
CurrentSize: 27, Step: 3
CurrentSize: 120, Step: 4
CurrentSize: 534, Step: 5
CurrentSize: 2348, Step: 6
CurrentSize: 10244, Step: 7
CurrentSize: 44241, Step: 8
CurrentSize: 186568, Step: 9
CurrentSize: 736513, Step: 10
CurrentSize: 2360094, Step: 11
CurrentSize: 4236655, Step: 12
CurrentSize: 2952910, Step: 13
CurrentSize: 491357, Step: 14
CurrentSize: 862, Step: 15
CurrentSize: 0, Step: 16
allSize: 11022480%  */