from collections import deque
class Quaternion:
    def __init__(self, w, x, y, z):
        self.w = w
        self.x = x
        self.y = y
        self.z = z
    def __eq__(self, value: object) -> bool: return self.w == value.w and self.x == value.x and self.y == value.y and self.z == value.z
    def __hash__(self) -> int: return hash((self.w, self.x, self.y, self.z))
    def multiply(self, other):
        return Quaternion(
            self.w * other.w - self.x * other.x - self.y * other.y - self.z * other.z,
            self.w * other.x + self.x * other.w + self.y * other.z - self.z * other.y,
            self.w * other.y - self.x * other.z + self.y * other.w + self.z * other.x,
            self.w * other.z + self.x * other.y - self.y * other.x + self.z * other.w
        )
    def negate(self): return Quaternion(self.w, -self.x, -self.y, -self.z)

class CubeManipulation:
    i = Quaternion(0, 1, 0, 0)    # Quaternion representing a 90-degree rotation around the x-axis
    j = Quaternion(0, 0, 1, 0)    # Quaternion representing a 90-degree rotation around the y-axis
    k = Quaternion(0, 0, 0, 1)    # Quaternion representing a 90-degree rotation around the z-axis

    @staticmethod
    def rotate_xy_clockwise(cube):
        # Rotate plane xy clockwise
        return [
            cube[4].multiply(CubeManipulation.i),  # Top face moves to the front
            cube[0].multiply(CubeManipulation.i),  # Front face moves to the left
            cube[2],  # unchanged
            cube[3],  # unchanged
            cube[5].multiply(CubeManipulation.i),  # Bottom face moves to the back
            cube[1].multiply(CubeManipulation.i),  # Back face moves to the right
            cube[6],  # unchanged
            cube[7]   # unchanged
        ]
    @staticmethod
    def rotate_xy_counterclockwise(cube):
        # Rotate plane xy counterclockwise
        return [
            cube[1].multiply(CubeManipulation.i).negate(),  # Back face moves to the front (negated)
            cube[5].multiply(CubeManipulation.i).negate(),  # Bottom face moves to the back (negated)
            cube[2],
            cube[3],  # unchanged
            cube[0].multiply(CubeManipulation.i).negate(),  # Front face moves to the left (negated)
            cube[4].multiply(CubeManipulation.i).negate(),  # Top face moves to the back (negated)
            cube[6],
            cube[7]   # unchanged
        ]
    @staticmethod
    def rotate_xz_clockwise(cube):
        # Rotate plane xz clockwise
        return [
            cube[2].multiply(CubeManipulation.j),  # Left face moves to the front
            cube[0].multiply(CubeManipulation.j),  # Front face moves to the left
            cube[3].multiply(CubeManipulation.j),  # Right face moves to the back
            cube[1].multiply(CubeManipulation.j),  # Back face moves to the right
            cube[4],
            cube[5],
            cube[6],
            cube[7]   # unchanged
        ]
    @staticmethod
    def rotate_xz_counterclockwise(cube):
        # Rotate plane xz counterclockwise
        return [
            cube[1].multiply(CubeManipulation.j).negate(),  # Back face moves to the front (negated)
            cube[3].multiply(CubeManipulation.j).negate(),  # Right face moves to the back (negated)
            cube[0].multiply(CubeManipulation.j).negate(),  # Front face moves to the left (negated)
            cube[2].multiply(CubeManipulation.j).negate(),  # Left face moves to the right (negated)
            cube[4],
            cube[5],
            cube[6],
            cube[7]   # unchanged
        ]
    @staticmethod
    def rotate_yz_clockwise(cube):
        # Rotate plane yz clockwise
        return [
            cube[0],  # unchanged
            cube[5].multiply(CubeManipulation.k),  # Bottom face moves to the back
            cube[2],  # unchanged
            cube[1].multiply(CubeManipulation.k),  # Back face moves to the right
            cube[4],  # unchanged
            cube[7].multiply(CubeManipulation.k),  # Top face moves to the back
            cube[6],  # unchanged
            cube[3].multiply(CubeManipulation.k)   # Right face moves to the front
        ]
    @staticmethod
    def rotate_yz_counterclockwise(cube):
        # Rotate plane yz counterclockwise
        return [
            cube[0],  # unchanged
            cube[3].multiply(CubeManipulation.k).negate(),  # Right face moves to the back (negated)
            cube[2],  # unchanged
            cube[7].multiply(CubeManipulation.k).negate(),  # Back face moves to the front (negated)
            cube[4],  # unchanged
            cube[1].multiply(CubeManipulation.k).negate(),  # Bottom face moves to the back (negated)
            cube[6],  # unchanged
            cube[5].multiply(CubeManipulation.k).negate()   # Top face moves to the front (negated)
        ]
    @staticmethod
    def go(cube, visited, step):
        queue = deque([cube])
        while queue:
            current_cube = queue.popleft()
            new_states = [
                CubeManipulation.rotate_xy_clockwise(current_cube),
                CubeManipulation.rotate_xy_counterclockwise(current_cube),
                CubeManipulation.rotate_xz_clockwise(current_cube),
                CubeManipulation.rotate_xz_counterclockwise(current_cube),
                CubeManipulation.rotate_yz_clockwise(current_cube),
                CubeManipulation.rotate_yz_counterclockwise(current_cube)
            ]
            old = visited.copy()
            for state in new_states:
                state_tuple = tuple(state)
                if state_tuple not in visited:
                    visited.add(state_tuple)
                    queue.append(state)
            print(len(visited)-len(old))

if __name__ == "__main__":
    initial_cube = tuple([Quaternion(i, 0, 0, 0) for i in range(1, 9)])
    CubeManipulation.go(initial_cube, {initial_cube}, 0)