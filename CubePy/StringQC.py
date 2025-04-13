import json
import sys
sys.setrecursionlimit(1000000000)
class Quaternion:
    def __init__(self, w, x, y, z):
        self.w = w
        self.x = x
        self.y = y
        self.z = z
        quaternions_array = [Quaternion(i, 0, 0, 0) for i in range(1, 9)]
    def multiply(self, other):
        return Quaternion(
            self.w * other.w - self.x * other.x - self.y * other.y - self.z * other.z,
            self.w * other.x + self.x * other.w + self.y * other.z - self.z * other.y,
            self.w * other.y - self.x * other.z + self.y * other.w + self.z * other.x,
            self.w * other.z + self.x * other.y - self.y * other.x + self.z * other.w
        )
    def __str__(self):
        return json.dumps([self.w, self.x, self.y, self.z])
    def __eq__(self, other):
        if not isinstance(other, Quaternion):
            return False
        return (self.w == other.w and
                self.x == other.x and
                self.y == other.y and
                self.z == other.z)

    def __hash__(self):
        return hash((self.w, self.x, self.y, self.z))

class CubeS:
    def __init__(self):
        quaternions_array = [Quaternion(1, 0, 0, 0), Quaternion(2, 0, 0, 0), Quaternion(3, 0, 0, 0), Quaternion(4, 0, 0, 0), 
            Quaternion(5, 0, 0, 0), Quaternion(6, 0, 0, 0), Quaternion(7, 0, 0, 0), Quaternion(8, 0, 0, 0)]
        rotation = [Quaternion(0, 1, 0, 0), Quaternion(0, -1, 0, 0), Quaternion(0, 0, 1, 0),
                    Quaternion(0, 0, -1, 0), Quaternion(0, 0, 0, 1), Quaternion(0, 0, 0, -1)]
        self.rotation_indices = [
            [4, 0, 5, 1],  # up
            [1, 5, 0, 4],  # down
            [2, 0, 3, 1],  # back
            [1, 3, 0, 2],  # front
            [1, 5, 7, 3],  # left
            [3, 7, 5, 1]   # right
        ]  # Defines the indices of each face of the cube for rotation
    def rotate_cube(self, index, indices, cube, rotation):
        result = list(cube)  # Create a copy of the cube
        for i in range(4):
            result[indices[index[i]]] = cube[indices[i]].multiply(rotation)
        return result
    def main(self):
        previous_states, difference, all_states = set(), set(), set()
        initial_state = json.dumps([str(q) for q in Quaternion.quaternions_array])
        all_states.add(initial_state)
        difference.add(initial_state)
        step = 0
        while difference:
            new_states = set()
            for state in difference:
                cube_state = [Quaternion(*q) for q in json.loads(state)]
                for i in range(6):
                    new_state = json.dumps([str(q) for q in self.rotate_cube(self.rotation_indices[i], cube_state, self.rotation[i])])
                    if new_state not in all_states:
                        new_states.add(new_state)
                        all_states.add(new_state)
            previous_states.update(difference)
            difference = new_states
            step += 1
            print(f"{len(previous_states)},{len(difference)},{len(all_states)},{step}")
        print(f"Total states: {len(all_states)}")
if __name__ == "__main__":
    CubeS().main()