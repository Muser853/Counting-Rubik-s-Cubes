class TRFaces:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z

    rotation = [
        __class__(1, 0, 0),
        __class__(1, 0, 0),
        __class__(0, 1, 0),
        __class__(0, 1, 0),
        __class__(0, 0, 1),
        __class__(0, 0, 1)
    ]
    ThreeFacesArray = [
        __class__(1, 0, 0),
        __class__(2, 0, 0),
        __class__(3, 0, 0),
        __class__(4, 0, 0),
        __class__(5, 0, 0),
        __class__(6, 0, 0),
        __class__(7, 0, 0),
        __class__(8, 0, 0)
    ]
    face_rotation_indices = [
        [4, 0, 5, 1],  # up
        [1, 5, 0, 4],  # down
        [2, 0, 3, 1],  # back
        [1, 3, 0, 2],  # front
        [1, 5, 7, 3],  # left
        [3, 7, 5, 1]   # right
    ]

    @staticmethod
    def rotate(index, indices, cube, rotation):
        result = cube.copy()
        for i in range(4):
            result[indices[index[i]]] = cube[indices[i]].multiply(rotation)
        return result

    def multiply(self, other):
        return TRFaces(
            self.y * other.z + self.z * other.y + self.x * other.x,
            self.z * other.x + self.x * other.z + self.y * other.y,
            self.x * other.y + self.y * other.x + self.z * other.z
        )

    def __eq__(self, other):
        if not isinstance(other, TRFaces):
            return False
        return self.x == other.x and self.y == other.y and self.z == other.z

    def __hash__(self):
        return hash((self.x, self.y, self.z))

    def __str__(self):
        return f"TRFaces({self.x}, {self.y}, {self.z})"
    __repr__ = __str__
