import copy
import sys
sys.setrecursionlimit(1000000000)
def clock(matrix):
    n = len(matrix)
    for i in range((n - 1) // 2 + 1):
        for j in range(i, n - 1 - i):
            temp = matrix[i][j]
            matrix[i][j] = matrix[n - 1 - j][i]
            matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j]
            matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i]
            matrix[j][n - 1 - i] = temp
    return matrix

def counterclock(matrix):
    n = len(matrix)
    for i in range((n - 1) // 2 + 1):
        for j in range(i, n - 1 - i):
            temp = matrix[i][j]
            matrix[i][j] = matrix[j][n - 1 - i]
            matrix[j][n - 1 - i] = matrix[n - 1 - i][n - 1 - j]
            matrix[n - 1 - i][n - 1 - j] = matrix[n - 1 - j][i]
            matrix[n - 1 - j][i] = temp
    return matrix

def u(A):
    B = clock([[A[i][0][j] for j in range(2)] for i in range(2)])
    for j in range(2):
        for i in range(2): A[i][0][j] = 8 - B[i][j]
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
        for k in range(2): A[0][i][k] = B[i][k]
    return A

def d(A):
    B = counterclock(A[0])
    for i in range(2):
        for k in range(2):
            if B[i][k] > 8: B[i][k] -= 8
            elif B[i][k] > 0: B[i][k] += 8
    for i in range(2):
        for k in range(2): A[0][i][k] = B[i][k]
    return A

def l(A):
    B = clock([[A[i][j][1] for j in range(2)] for i in range(2)])
    for j in range(2):
        for i in range(2): A[i][j][1] = 8 - B[i][j]
    return A

def j(A):
    B = counterclock([[A[i][j][1] for j in range(2)] for i in range(2)])
    for j in range(2):
        for i in range(2): A[i][j][1] = 8 - B[i][j]
    return A

moves = [u, n, b, d, l, j] # list of functions

def go(cube, visited, length, count):
    a = []
    for move in moves:
        temp_cube = copy.deepcopy(cube)
        moved = move(temp_cube)
        if moved not in visited: a.append(moved)
    visited.extend(a) 

    # Breadth-first search
    if len(visited) == length:  return length, count  # Return the length and count when base case is reached
    for i in range(length, len(visited)):
        return go(visited[i], visited, len(visited), count + 1)  # Capture the result of the recursive call

icube = [[[1, 2], [3, 4]], [[5, 6], [7, 8]]]
visited = [icube]
length, count = go(icube, visited, 1, 0)
print(f"Visited: {length}, Count: {count}") # maximum recursion depth exceeded