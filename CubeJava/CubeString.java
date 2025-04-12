import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
public class CubeString {
    public static void main(String[] args) {
        HashSet<String> pre = new HashSet<>(), now = new HashSet<>(), next = new HashSet<>();
        now.add(Arrays.toString(TRFaces.ThreeFacesArray.toArray()));
        int step = 0;
        do {
            for (String state : now) {
                TRFaces[] cubeState = Arrays.stream(state.substring(1, state.length() - 1).split(","))
                        .map(str -> {
                            String content = str.replaceFirst("^\\s*TRFaces\\(", "").replaceFirst("\\)$", "");
                            String[] parts = content.split(",");
                            return new TRFaces(
                                Integer.parseInt(parts[0].trim()),
                                Integer.parseInt(parts[1].trim()),
                                Integer.parseInt(parts[2].trim())
                            );
                        })
                    .toArray(TRFaces[]::new);
                for (int i = 0; i < 6; i++)
                    next.add(Arrays.toString(TRFaces.rotate(TRFaces.faceRotationIndices[2 + ((i+1) % 2)],
                        TRFaces.faceRotationIndices[i], new ArrayList<>(Arrays.asList(cubeState)),
                        TRFaces.rotation[i]).toArray()));
            }
            now.clear();
            for (String stat : next) if (pre.add(stat)) now.add(stat);
            System.out.printf("pre states: %d, now states: %d, next states: %d, step: %d\n", pre.size(), now.size(), next.size(), ++step);
            next.clear();
        } while (!now.isEmpty());
    }
}