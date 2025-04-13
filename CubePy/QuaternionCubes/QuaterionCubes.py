class Quaternion:
    def __init__(self, w, x, y, z):
        self.w = w
        self.x = x
        self.y = y
        self.z = z
    def __eq__(self, value: object) -> bool: return self.w == value.w and self.x == value.x and self.y == value.y and self.z == value.z
    def __hash__(self) -> int: return hash((self.w, self.x, self.y, self.z))
    def multiply(self, other):
        # Quaternion multiplication implementation
        return Quaternion(
            self.w * other.w - self.x * other.x - self.y * other.y - self.z * other.z,
            self.w * other.x + self.x * other.w + self.y * other.z - self.z * other.y,
            self.w * other.y - self.x * other.z + self.y * other.w + self.z * other.x,
            self.w * other.z + self.x * other.y - self.y * other.x + self.z * other.w
        )
    def conjugate(self): return Quaternion(self.w, -self.x, -self.y, -self.z)

quaternion_i = Quaternion(0, 1, 0, 0)  # Quaternion representing a 90-degree rotation around x-axis
quaternion_j = Quaternion(0, 0, 1, 0)  # around y-axis
quaternion_k = Quaternion(0, 0, 0, 1)  # around z-axis

def rotate_up(cube):
    return [
        cube[4].multiply(quaternion_i),  # RIGHT BACK CUBE -> RIGHT FRONT
        cube[0].multiply(quaternion_i),  # RIGHT FRONT CUBE -> LEFT FRONT
        cube[2],
        cube[3],
        cube[5].multiply(quaternion_i),  # LEFT BACK CUBE -> RIGHT BACK
        cube[1].multiply(quaternion_i),  # LEFT FRONT CUBE -> LEFT BACK
        cube[6],
        cube[7]
    ]  # ROTATE PLANE XY CLOCKWISE WITH CUBES IN POSITIONS 3,4,7,8 FIXED
def rotate_down(cube):
    return [
        cube[1].multiply(quaternion_i.conjugate()),  # LEFT FRONT CUBE -> RIGHT FRONT
        cube[5].multiply(quaternion_i.conjugate()),  # LEFT BACK CUBE -> LEFT FRONT
        cube[2],
        cube[3],
        cube[0].multiply(quaternion_i.conjugate()),  # RIGHT FRONT CUBE -> RIGHT BACK
        cube[4].multiply(quaternion_i.conjugate()),  # RIGHT BACK CUBE -> LEFT BACK
        cube[6],
        cube[7]
    ]  # ROTATE PLANE XY COUNTERCLOCKWISE
def rotate_back(cube):
    return [
        cube[2].multiply(quaternion_j),  # UPPER RIGHT CUBE -> UPPER LEFT
        cube[0].multiply(quaternion_j),  # UPPER LEFT CUBE -> LOWER LEFT
        cube[3].multiply(quaternion_j),  # LOWER LEFT CUBE -> LOWER RIGHT
        cube[1].multiply(quaternion_j),  # LOWER RIGHT CUBE -> UPPER RIGHT
        cube[4],
        cube[5],
        cube[6],
        cube[7]
    ]  # ROTATE PLANE XZ CLOCKWISE WITH CUBES IN POSITIONS 5,6,7,8 FIXED
def rotate_front(cube):
    return [
        cube[1].multiply(quaternion_j.conjugate()),  # UPPER LEFT CUBE -> UPPER RIGHT
        cube[3].multiply(quaternion_j.conjugate()),  # UPPER RIGHT CUBE -> LOWER RIGHT
        cube[0].multiply(quaternion_j.conjugate()),  # LOWER RIGHT CUBE -> LOWER LEFT
        cube[2].multiply(quaternion_j.conjugate()),  # LOWER LEFT CUBE -> UPPER LEFT
        cube[4],
        cube[5],
        cube[6],
        cube[7]
    ]  # ROTATE PLANE XZ COUNTERCLOCKWISE
def rotate_left(cube):
    return [
        cube[0],
        cube[5].multiply(quaternion_k),  # UPPER BACK CUBE -> UPPER FRONT
        cube[2],
        cube[1].multiply(quaternion_k),  # UPPER FRONT CUBE -> LOWER FRONT
        cube[4],
        cube[7].multiply(quaternion_k),  # LOWER FRONT CUBE -> LOWER BACK
        cube[6],
        cube[3].multiply(quaternion_k)  # LOWER BACK CUBE -> UPPER BACK
    ]  # ROTATE PLANE YZ CLOCKWISE WITH CUBES IN POSITIONS 1,3,5,7 FIXED
def rotate_right(cube):
    return [
        cube[0],
        cube[3].multiply(quaternion_k.conjugate()),  # LOWER FRONT CUBE -> UPPER FRONT
        cube[2],
        cube[7].multiply(quaternion_k.conjugate()),  # UPPER FRONT CUBE -> UPPER BACK
        cube[4],
        cube[1].multiply(quaternion_k.conjugate()),  # UPPER BACK CUBE -> LOWER BACK
        cube[6],
        cube[5].multiply(quaternion_k.conjugate())  # LOWER BACK CUBE -> LOWER FRONT
    ]
def go(old_visit, all_visited, step):
    new_old = all_visited.copy()
    new_states = set() 
    for cube in all_visited - old_visit:
        new_states.add(tuple(rotate_down(cube)))
        new_states.add(tuple(rotate_up(cube)))
        new_states.add(tuple(rotate_back(cube)))
        new_states.add(tuple(rotate_front(cube)))
        new_states.add(tuple(rotate_left(cube)))
        new_states.add(tuple(rotate_right(cube))) 

    all_visited.update(new_states) 
    if len(all_visited) == len(new_old): return len(all_visited), step  
    print(len(all_visited) - len(new_old), ",", step)
    go(new_old, all_visited, step + 1)# Recursively visit new states in breadth-first search

if __name__ == "__main__":  go(set(), {tuple(Quaternion(i, 0, 0, 0) for i in range(1, 9))}, 0)  # Initialize Rubik's Cube of size 2 with 8 quaternions representing 8 element cubes