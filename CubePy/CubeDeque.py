from collections import deque
from CubePyMap import Quaternion
rotation = [
        Quaternion(0, 1, 0, 0),
        Quaternion(0, -1, 0, 0),
        Quaternion(0, 0, 1, 0),
        Quaternion(0, 0, -1, 0),
        Quaternion(0, 0, 0, 1),
        Quaternion(0, 0, 0, -1)
    ]
initial_state = tuple(Quaternion(i, 0, 0, 0) for i in range(1, 9))
queue = deque([initial_state])
steps = {initial_state: 0}
max_depth = 0

while queue:
    current_state = queue.popleft()
    current_step = steps[current_state]
    max_depth = max(max_depth, current_step)

    for i in range(6):
        new_state = tuple(Quaternion.rotate(Quaternion.face_rotation_indices[2 + ((i+1) % 2)], Quaternion.face_rotation_indices[i], list(current_state), rotation[i]))
        if new_state not in steps:
            steps[new_state] = current_step + 1
            queue.append(new_state)
    
print(f"Maximum depth: {max_depth}, Total states: {len(steps)}")