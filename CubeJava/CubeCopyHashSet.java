import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class CubeCopyHashSet {
    public static int step = 0, batchSize;
    public static Set<List<TRFaces>> all = new HashSet<>(Arrays.asList(TRFaces.ThreeFacesArray));
    // Use a queue to track elements to process in each step
    private static Queue<List<TRFaces>> processingQueue = new LinkedList<>(all);
    private static List<TRFaces> cube;

    public static void main(String[] args) {
        do {
            batchSize = processingQueue.size();
            // Process current batch
            for (int i = 0; i < batchSize; i++) {
                cube = processingQueue.poll();
                for (int j = 0; j < 6; j++) {
                    List<TRFaces> rotated = TRFaces.rotate(TRFaces.faceRotationIndices[2 + j % 2], TRFaces.faceRotationIndices[j], cube, TRFaces.rotation[j]);
                    // Add to set and queue if new
                    if (all.add(rotated)) processingQueue.add(rotated);
                }
            }
            System.out.printf("batchSize: %d, step: %d\n", batchSize, ++step);
        } while (!processingQueue.isEmpty());
        System.out.printf("allSize: %d", all.size());
    }
}/*batchSize: 1, step: 1
batchSize: 6, step: 2
batchSize: 27, step: 3
batchSize: 120, step: 4
batchSize: 534, step: 5
batchSize: 2376, step: 6
batchSize: 10546, step: 7
batchSize: 46700, step: 8
batchSize: 206244, step: 9
batchSize: 908250, step: 10 */