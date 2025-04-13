import copy
def clock(matrix):
    n = len(matrix)
    rotated = [row[:] for row in matrix] 
    for i in range((n - 1) // 2 + 1):
        for j in range(i, n - 1 - i):
            temp = rotated[i][j]
            rotated[i][j] = rotated[n-1-j][i]
            rotated[n-1-j][i] = rotated[n-1-i][n-1-j]
            rotated[n-1-i][n-1-j] = rotated[j][n-1-i]
            rotated[j][n-1-i] = temp
    return rotated

def counterclock(matrix):
    n = len(matrix)
    rotated = [row[:] for row in matrix]
    for i in range((n - 1) // 2 + 1):
        for j in range(i, n - 1 - i):
            temp = rotated[i][j]
            rotated[i][j] = rotated[j][n-1-i]
            rotated[j][n-1-i] = rotated[n-1-i][n-1-j]
            rotated[n-1-i][n-1-j] = rotated[n-1-j][i]
            rotated[n-1-j][i] = temp
    return rotated

def u(cube):
    new_cube = copy.deepcopy(cube) 
    B = clock([row[:] for row in new_cube[0][:2]])
    for i in range(2):
        for k in range(2): B[i][k] = 8 - B[i][k]
    for j in range(2):
        for i in range(2): new_cube[0][i][j] = B[i][j]
    return new_cube

def n(cube):
    new_cube = copy.deepcopy(cube)
    B = [row[:] for row in new_cube[0][:2]]
    B = counterclock(B)
    for j in range(2):
        for i in range(2):
            new_cube[0][i][j] = 8 - B[i][j]
    return new_cube

def b(cube):
    new_cube = copy.deepcopy(cube)
    B = clock(new_cube[0])
    for i in range(2):
        for k in range(2):
            if B[i][k] > 8: B[i][k] -= 8
            elif B[i][k] > 0: B[i][k] += 8
    new_cube[0] = B
    return new_cube

def d(cube):
    new_cube = copy.deepcopy(cube)
    B = counterclock(new_cube[0])
    for i in range(2):
        for k in range(2):
            if B[i][k] > 8: B[i][k] -= 8
            elif B[i][k] > 0: B[i][k] += 8
    new_cube[0] = B
    return new_cube

def l(cube):
    new_cube = copy.deepcopy(cube)
    B = clock([row[:] for row in new_cube[1]] )
    for j in range(2):
        for i in range(2): new_cube[1][i][j] = -B[i][j]
    return new_cube

def j(cube):
    new_cube = copy.deepcopy(cube)
    B = counterclock([row[:] for row in new_cube[1]])
    for j in range(2):
        for i in range(2): new_cube[1][i][j] = -B[i][j]
    return new_cube

moves = [u, n, b, d, l, j]

def bfs(initial_cube):
    visited = set()
    queue = [(initial_cube, 0)]
    visited.add(tuple( tuple( tuple(row) for row in face ) for face in initial_cube ))
    max_depth = 0

    while queue:
        current_cube, depth = queue.pop(0)
        max_depth = max(max_depth, depth)
        for move in moves:
            new_cube = move(current_cube)
            new_state = tuple( tuple( tuple(row) for row in face ) for face in new_cube ) 
            if new_state not in visited:
                visited.add(new_state)
                queue.append((new_cube, depth + 1))
    return max_depth, len(visited) 

icube = [ # initialize the cube
    [[1, 2], [3, 4]],  
    [[5, 6], [7, 8]] 
]
max_depth, total_states = bfs(icube)
print(f"Maximum rotation depth: {max_depth}, Total states: {total_states}") #8 , 96