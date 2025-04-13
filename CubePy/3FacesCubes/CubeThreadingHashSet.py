from collections import OrderedDict
from threading import Lock
from TRFaces import TRFaces

class ThreadSafeOrderedSet:
    def __init__(self, initial_element):
        self.lock = Lock()
        self._elements = OrderedDict()
        self._elements[initial_element] = None
        self.size = 1

    def add(self, element):
        with self.lock:
            if element not in self._elements:
                self._elements[element] = None
                self.size += 1

    def iterator(self):
        with self.lock:
            return iter(list(self._elements.keys()))

class CubeLinkedHashSet:
    step = 0
    size = 1
    all = ThreadSafeOrderedSet(TRFaces.ThreeFacesArray)

    @classmethod
    def main(cls):
        while True:
            with cls.all.lock:
                current_size = cls.size
                new_size = cls.all.size - current_size
                if new_size <= 0: break
                
                iterator = cls.all.iterator()
                elements_to_process = [next(iterator) for _ in range(current_size)]

            for cube in elements_to_process:
                for j in range(6):
                    rotated = TRFaces.rotate(
                        TRFaces.face_rotation_indices[2 + j % 2],
                        TRFaces.face_rotation_indices[j],
                        cube,
                        TRFaces.rotation[j]
                    )
                    cls.all.add(rotated)

            cls.size = new_size
            cls.step += 1
            print(f"addStates: {cls.size}, step: {cls.step}")

        print(f"allSize: {cls.all.size}")

if __name__ == "__main__": CubeLinkedHashSet.main()