class SinglyLinkedHashSet:
    class Entry:
        def __init__(self, element):
            self.element = element
            self.after = None

    def __init__(self):
        self.head = None
        self.tail = None
        self.size = 0
        self._set = set()

    def add(self, element):
        if element in self._set: return

        self._set.add(element)
        new_entry = self.Entry(element)

        if self.tail is None:
            self.head = self.tail = new_entry
        else:
            self.tail.after = new_entry
            self.tail = new_entry

        self.size += 1

from TRFaces import TRFaces
class CubeEntryHashSet:
    step = 0
    size = 1
    all = SinglyLinkedHashSet()

    @classmethod
    def main(cls):
        cls.all.add(TRFaces.ThreeFacesArray)
        n = cls.all.head
        
        while n is not None:
            current_size = cls.size
            cls.size = cls.all.size - cls.size

            for _ in range(current_size):
                if n is None: break

                for j in range(6):
                    rotated = TRFaces.rotate(
                        TRFaces.face_rotation_indices[2 + j % 2], TRFaces.face_rotation_indices[j],
                        n.element, TRFaces.rotation[j]
                    )
                    cls.all.add(rotated)
                n = n.after

            cls.step += 1
            print(f"addStates: {current_size}, step: {cls.step}")
            
        print(f"allSize: {cls.all.size}")

if __name__ == "__main__": CubeEntryHashSet.main()