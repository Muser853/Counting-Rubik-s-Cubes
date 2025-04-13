# This code represents a Quaternion class and a CubeS class which uses quaternions for rotation operations.
class Quaternion:
    def __init__(self, w: float, x: float, y: float, z: float):
        """Initialize a Quaternion with real and imaginary parts."""
        self.w = w  # real part
        self.x = x  # imaginary part representing 90-degree rotation around x-axis
        self.y = y  # around y-axis
        self.z = z  # around z-axis

    def multiply(self, other: 'Quaternion') -> 'Quaternion':
        """Multiply this quaternion by another quaternion."""
        return Quaternion(
            self.w * other.w - self.x * other.x - self.y * other.y - self.z * other.z,
            self.w * other.x + self.x * other.w + self.y * other.z - self.z * other.y,
            self.w * other.y - self.x * other.z + self.y * other.w + self.z * other.x,
            self.w * other.z + self.x * other.y - self.y * other.x + self.z * other.w
        )
    def __str__(self) -> str: return f"Quaternion({self.w}, {self.x}, {self.y}, {self.z})"
    def __eq__(self, other: object) -> bool: return (self.w, self.x, self.y, self.z) == (other.w, other.x, other.y, other.z)
    def __hash__(self) -> int: return hash((self.w, self.x, self.y, self.z))

class CubeS:
    rotation_indices = [
        [4, 0, 5, 1],  # up
        [1, 5, 0, 4],  # down
        [2, 0, 3, 1],  # back
        [1, 3, 0, 2],  # front
        [1, 5, 7, 3],  # left
        [3, 7, 5, 1]   # right
    ]  # Defines the indices of each face of the cube for rotation
    @staticmethod
    def rotate_cube(index: list[int], indices: list[int], cube: list[Quaternion], rotation: Quaternion) -> list[Quaternion]:
        result = cube[:]  # copy
        for i in range(4): result[indices[index[i]]] = result[indices[i]].multiply(rotation)
        return result #Rotate the cube based on the given indices and rotation quaternion.

    @staticmethod
    def main() -> None:
        quaternions_array = [Quaternion(i, 0, 0, 0) for i in range(1, 9)]
        all_states = {tuple(quaternions_array)}
        difference = {tuple(quaternions_array)}
        step = 0
        # Store the quaternion rotations in a list
        rotation = [Quaternion(0, 1, 0, 0), Quaternion(0, -1, 0, 0), Quaternion(0, 0, 1, 0), Quaternion(0, 0, -1, 0), Quaternion(0, 0, 0, 1), Quaternion(0, 0, 0, -1)]
        while difference:
            previous = all_states.copy()
            new_states = set()

            for cube_state in difference:
                cube_state_list = list(cube_state)  # Convert once
                for i in range(6):  new_states.add(tuple(CubeS.rotate_cube(CubeS.rotation_indices[2 + ((i+1) % 2)], CubeS.rotation_indices[i], cube_state_list, rotation[i])))

            all_states.update(new_states)
            difference = new_states - previous 
            step += 1
            print(f"{len(previous)},{len(difference)},{len(all_states)},{step}")
        print(f"Total states: {len(all_states)}")

if __name__ == "__main__":
    CubeS.main()
'''1,6,7,1
7,23,30,2
30,52,82,3
82,70,152,4
152,81,233,5
233,106,339,6
339,130,469,7
469,147,616,8
616,163,779,9
779,159,938,10
938,120,1058,11
1058,87,1145,12
1145,64,1209,13
1209,42,1251,14
1251,28,1279,15
1279,12,1291,16
1291,4,1295,17
1295,0,1295,18
Total states: 1295
'''