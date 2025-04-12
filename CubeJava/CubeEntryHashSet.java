import java.util.List;

public class CubeEntryHashSet {
    public static int step = 0, size = 1;
    public static SinglyLinkedHashSet<List<TRFaces>> all = new SinglyLinkedHashSet<>();
    public static void main(String[] args) {
        all.add(TRFaces.ThreeFacesArray);
        SinglyLinkedHashSet.Entry<List<TRFaces>> n = all.head;
        while (n != null) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < 6; j++) {
                    all.add(TRFaces.rotate(TRFaces.faceRotationIndices[2 + j % 2], TRFaces.faceRotationIndices[j],
                    n.element, TRFaces.rotation[j]));
                }
                n = n.after;
                if (n == null) break;
            }
            size = all.size() - size;
            System.out.printf("addStates: %d, step: %d\n", size, ++step);
        }
        System.out.printf("allSize: %d", all.size());
    }
} /*addStates: 6, step: 1
addStates: 28, step: 2
addStates: 131, step: 3
addStates: 611, step: 4
addStates: 2821, step: 5
addStates: 12909, step: 6
addStates: 58613, step: 7
addStates: 260276, step: 8
addStates: 1084359, step: 9
addStates: 3652195, step: 10
addStates: 6361743, step: 11
Exception in thread "main" java.lang.NullPointerException: Cannot read field "element" because "<local1>" is null
        at CubeEntryHashSet.main(CubeEntryHashSet.java:14)

addStates: 6361743, step: 11
addStates: 4660737, step: 12
allSize: 11022480
 */