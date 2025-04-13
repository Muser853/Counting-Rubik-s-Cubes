import collections
class Quaternion:
    def __init__(self, w, x, y, z):
        self.w = w
        self.x = x
        self.y = y
        self.z = z

    face_rotation_indices = [
        [4, 0, 5, 1],  # up
        [1, 5, 0, 4],  # down
        [2, 0, 3, 1],  # back
        [1, 3, 0, 2],  # front
        [1, 5, 7, 3],  # left
        [3, 7, 5, 1]   # right
    ]
    def multiply(self, other):
        return Quaternion(
            self.w * other.w - self.x * other.x - self.y * other.y - self.z * other.z,
            self.w * other.x + self.x * other.w + self.y * other.z - self.z * other.y,
            self.w * other.y - self.x * other.z + self.y * other.w + self.z * other.x,
            self.w * other.z + self.x * other.y - self.y * other.x + self.z * other.w
        )
    @staticmethod
    def rotate(index, indices, cube, rotation):
        result = cube[:]
        for i in range(4): result[indices[index[i]]] = result[index[i]].multiply(rotation)
        return result

    def __str__(self): return f"Quaternion({self.w}, {self.x}, {self.y}, {self.z})"
    def __eq__(self, other): return (self.w, self.x, self.y, self.z) == (other.w, other.x, other.y, other.z)
    def __hash__(self): return hash((self.w, self.x, self.y, self.z))

def main():
    rotation = [
        Quaternion(0, 1, 0, 0),
        Quaternion(0, -1, 0, 0),
        Quaternion(0, 0, 1, 0),
        Quaternion(0, 0, -1, 0),
        Quaternion(0, 0, 0, 1),
        Quaternion(0, 0, 0, -1)
    ]
    all_states = collections.OrderedDict()
    all_states[tuple(Quaternion(i, 0, 0, 0) for i in range(1, 9))] = True
    step = 0
    while True:
        entry = next((e for e in all_states.items() if e[1]), None)
        if entry is None: break
        state, in_queue = entry
        all_states[state] = False
        for i in range(6):
            rotated_cube = Quaternion.rotate(Quaternion.face_rotation_indices[2 + ((i+1) % 2)], Quaternion.face_rotation_indices[i], list(state), rotation[i])
            all_states.setdefault(tuple(rotated_cube), True)
        step += 1
        queue_size = sum(1 for in_queue in all_states.values() if in_queue)
        print(f"differenceQueue: {queue_size}, allStates: {len(all_states)}, step: {step}")

if __name__ == "__main__": main()