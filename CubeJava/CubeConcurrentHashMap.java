import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.HashSet;

public class CubeConcurrentHashMap {
    public static Map<List<TRFaces>, Integer> states = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        states.put(TRFaces.ThreeFacesArray, 0);
        for (int step = 0; states.containsValue(step); System.out.printf("States: %d, Step: %d%n", states.size(), ++step)){
            for (Map.Entry<List<TRFaces>, Integer> entry : states.entrySet()) {
                if (entry.getValue() == step){
                    for (int i = 0; i < 6; i++) {
                      states.putIfAbsent(TRFaces.rotate(TRFaces.faceRotationIndices[2 + i % 2],
                        TRFaces.faceRotationIndices[i], entry.getKey(), TRFaces.rotation[i]), step + 1);
                    }
                }
            }
        }
        int[][][] counts = new int[8][8][3];
        for (List<TRFaces> state : states.keySet()) {
            for (int j = 0; j < 8; j++) {
              TRFaces tf = state.get(j);
              if (tf.x != 0) {
                counts[j][tf.x - 1][0]++;
              }
              else if (tf.y != 0){ 
                counts[j][tf.y - 1][1]++;
              }
              else{
                counts[j][tf.z - 1][2]++; 
              }
            }
        }
        // Loop through the 8 positions
        for (int j = 0; j <8; j++) {
            // Print the position and the counts
            System.out.printf("Position %d counts:%n", j);
            // Loop through the 8 numbers
            for (int i =1; i<=8; i++) {
              // Loop through the 3 counts
              for (int k = 0; k < 3; k++){
                // Print the count of the number
                System.out.printf("  count of %d: %d%n", i, counts[j][i-1][k]);
              }
            }
            System.out.println("-------------------------------");
        }
    }
}/*States: 7, Step: 1
States: 34, Step: 2
States: 154, Step: 3
States: 688, Step: 4
States: 3036, Step: 5
States: 13280, Step: 6
States: 57521, Step: 7
States: 244089, Step: 8
States: 980602, Step: 9
States: 3340696, Step: 10
States: 7577351, Step: 11
States: 10530261, Step: 12
States: 11021618, Step: 13
States: 11022480, Step: 14

Position 0 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880
-------------------------------
Position 1 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880
-------------------------------
Position 2 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880
-------------------------------
Position 3 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880
-------------------------------
Position 4 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880
-------------------------------
Position 5 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880
-------------------------------
Position 6 counts:
  count of 1: 0
  count of 1: 0
  count of 1: 0
  count of 2: 0
  count of 2: 0
  count of 2: 0
  count of 3: 0
  count of 3: 0
  count of 3: 0
  count of 4: 0
  count of 4: 0
  count of 4: 0
  count of 5: 0
  count of 5: 0
  count of 5: 0
  count of 6: 0
  count of 6: 0
  count of 6: 0
  count of 7: 11022480
  count of 7: 0
  count of 7: 0
  count of 8: 0
  count of 8: 0
  count of 8: 0
-------------------------------
Position 7 counts:
  count of 1: 524880
  count of 1: 524880
  count of 1: 524880
  count of 2: 524880
  count of 2: 524880
  count of 2: 524880
  count of 3: 524880
  count of 3: 524880
  count of 3: 524880
  count of 4: 524880
  count of 4: 524880
  count of 4: 524880
  count of 5: 524880
  count of 5: 524880
  count of 5: 524880
  count of 6: 524880
  count of 6: 524880
  count of 6: 524880
  count of 7: 0
  count of 7: 0
  count of 7: 0
  count of 8: 524880
  count of 8: 524880
  count of 8: 524880 */