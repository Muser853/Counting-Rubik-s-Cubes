import copy
import sys
sys.setrecursionlimit(1000000000)
def clock(matrix):
    m = len(matrix)
    for i in range((m - 1) // 2 + 1):
        for j in range(i, m - 1 - i):
            temp = matrix[i][j]
            matrix[i][j] = matrix[m - 1 - j][i]
            matrix[m - 1 - j][i] = matrix[m - 1 - i][m - 1 - j]
            matrix[m - 1 - i][m - 1 - j] = matrix[j][m - 1 - i]
            matrix[j][m - 1 - i] = temp
    return matrix

def counterclock(matrix):
    m = len(matrix)
    for i in range((m - 1) // 2 + 1):
        for j in range(i, m - 1 - i):
            temp = matrix[i][j]
            matrix[i][j] = matrix[j][m - 1 - i]
            matrix[j][m - 1 - i] = matrix[m - 1 - i][m - 1 - j]
            matrix[m - 1 - i][m - 1 - j] = matrix[m - 1 - j][i]
            matrix[m - 1 - j][i] = temp
    return matrix

def u(A):
    B = clock([[A[i][0][j] for j in range(2)] for i in range(2)])
    for i in range(2):
        for k in range(2): B[i][k] = 8 - B[i][k]

    for j in range(2):
        for i in range(2): A[i][0][j] = B[i][j]
    return A

def n(A):
    B = counterclock([[A[i][0][j] for j in range(2)] for i in range(2)])
    for j in range(2):
        for i in range(2): A[i][0][j] = 8 - B[i][j]
    return A

def b(A):
    B = clock(A[0])
    for i in range(2):
        for k in range(2):
            if B[i][k] > 8: B[i][k] -= 8
            elif B[i][k] > 0: B[i][k] += 8
    for i in range(2):
        for k in range(2):
            A[0][i][k] = B[i][k]
    return A

def d(A):
    B = counterclock(A[0])
    for i in range(2):
        for k in range(2):
                if B[i][k] > 8: B[i][k] -= 8
                elif B[i][k] > 0: B[i][k] += 8
    for i in range(2):
        for k in range(2):
            A[0][i][k] = B[i][k]
    return A

def l(A):
    B = clock([[A[i][j][1] for j in range(2)] for i in range(2)])
    for j in range(2):
        for i in range(2): A[i][j][1] = -B[i][j]
    return A

def j(A):
    B = [[A[i][j][1] for j in range(2)] for i in range(2)]
    B = counterclock(B)
    for j in range(2):
        for i in range(2): A[i][j][1] = -B[i][j]
    return A

# Create list of movement functions
moves = [u, n, b, d, l, j]
def go(cube, visited):
    current_state = tuple(tuple(tuple(row) for row in face) for face in cube)
    if current_state in visited: return
    visited.add(current_state)
    
    for move in moves:
        c = move(copy.deepcopy(cube))
        go(c, visited)
    return len(visited)

def boggle(cube):
    total_states = go(cube, set())
    print(f"Total unique states: {total_states}")
    return total_states

initial_cube = [[[1, 2], [3, 4]], [[5, 6], [7, 8]]]
boggle(initial_cube) # maximum recursion depth exceeded while calling a Python object