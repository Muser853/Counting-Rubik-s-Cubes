from TRFaces import TRFaces
import sys
sys.setrecursionlimit(1000000000)
from collections import deque
i = TRFaces(1, 0, 0)
j = TRFaces(0, 1, 0)
k = TRFaces(0, 0, 1)
def u(cube): return [cube[4]*i, cube[0]*i, cube[2], cube[3], cube[5]*i, cube[1]*i, cube[6], cube[7]]
def n(cube): return [-cube[1]*i, -cube[5]*i, cube[2], cube[3], -cube[0]*i, -cube[4]*i, cube[6], cube[7]]
def b(cube): return [cube[2]*j, cube[0]*j, cube[3]*j, cube[1]*j, cube[4], cube[5], cube[6], cube[7]]
def d(cube): return [-cube[2]*j, -cube[0]*j, -cube[3]*j, -cube[1]*j, cube[4], cube[5], cube[6], cube[7]]
def l(cube): return [cube[0], cube[5]*k, cube[2], cube[1]*k, cube[4], cube[5], cube[6], cube[3]*k]
def j_move(cube): return [cube[0], -cube[3]*k, cube[2], -cube[7]*k, cube[4], -cube[1]*k, cube[6], -cube[5]*k]
moves = [u, n, b, d, l, j_move] 
def bfs(initial_cube):
    visited = set()
    queue = deque()
    initial_state = tuple(initial_cube)
    queue.append((initial_state, 0))
    visited.add(initial_state)
    max_depth = 0
    total_states = 0
    
    while queue:
        current_state, depth = queue.popleft()
        total_states += 1
        max_depth = max(max_depth, depth)
        
        for move in moves:
            new_cube = move(list(current_state))
            new_state = tuple(new_cube)

            if new_state not in visited:
                visited.add(new_state)
                queue.append((new_state, depth + 1))
    print(f"Total states: {total_states}, Total Layers of Rotations: {max_depth}")

icube = [TRFaces(i, 0, 0) for i in range(8)]
bfs(icube) # test all cube states