from TRFaces import TRFaces
class CubeHashMap:
    states = {}
    current_level = []
    entr = None
    size = 1

    @classmethod
    def main(cls):
        initial_state = tuple(TRFaces.threeFacesArray)
        cls.states[initial_state] = 0
        step = 0

        while cls.size != 0:
            cls.current_level = [k for k, v in cls.states.items() if v == step]
            cls.size = len(cls.current_level)
            if cls.size == 0: break

            for _ in range(cls.size):
                current_state = cls.current_level.pop()
                for i in range(6):
                    rotated = TRFaces.rotate(
                        TRFaces.face_rotation_indices[2 + i % 2],
                        TRFaces.face_rotation_indices[i],
                        list(current_state),
                        TRFaces.rotation[i]
                    )
                    rotated_tuple = tuple(rotated)
                    if rotated_tuple not in cls.states: cls.states[rotated_tuple] = step + 1

            step += 1
            print(f"CurrentSize: {cls.size}, Step: {step}")

        print(f"allSize: {len(cls.states)}")

if __name__ == "__main__": CubeHashMap.main()
