from TRFaces import TRFaces

class CubeConcurrentHashMap:
    states = {}
    _lock = None 
    @classmethod
    def main(cls):
        initial_state = tuple(TRFaces.ThreeFacesArray)
        cls.states = {initial_state: 0}
        step = 0

        while any(v == step for v in cls.states.values()):
            current_states = [k for k, v in cls.states.items() if v == step]
            
            for state in current_states:
                for i in range(6):
                    rotated = TRFaces.rotate(
                        TRFaces.face_rotation_indices[2 + i % 2],
                        TRFaces.face_rotation_indices[i],
                        list(state),
                        TRFaces.rotation[i]
                    )
                    rotated_tuple = tuple(rotated)
                    if rotated_tuple not in cls.states: cls.states[rotated_tuple] = step + 1

            print(f"States: {len(cls.states)}, Step: {step + 1}")
            step += 1

        counts = [[[0 for _ in range(3)] for _ in range(8)] for _ in range(8)]
        
        for state in cls.states.keys():
            for j in range(8):
                tf = state[j]
                if tf.x != 0:
                    counts[j][tf.x - 1][0] += 1
                elif tf.y != 0:
                    counts[j][tf.y - 1][1] += 1
                else:
                    counts[j][tf.z - 1][2] += 1

        for j in range(8):
            print(f"Position {j} counts:")
            for i in range(1, 9):
                for k in range(3):
                    print(f"  count of {i}: {counts[j][i-1][k]}")
            print("-------------------------------")

if __name__ == "__main__":    CubeConcurrentHashMap.main()