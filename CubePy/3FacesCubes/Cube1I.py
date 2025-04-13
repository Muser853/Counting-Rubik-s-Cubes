from TRFaces import TRFaces
i = TRFaces(1, 0, 0)
j = TRFaces(0, 1, 0)
k = TRFaces(0, 0, 1)
def u(cube): return [cube[4]*i, cube[0]*i, cube[2], cube[3], cube[5]*i, cube[1]*i, cube[6], cube[7]]
def n(cube): return [-cube[1]*i, -cube[5]*i, cube[2], cube[3], -cube[0]*i, -cube[4]*i, cube[6], cube[7]]
def b(cube): return [cube[2]*j, cube[0]*j, cube[3]*j, cube[1]*j, cube[4], cube[5], cube[6], cube[7]]
def d(cube): return [-cube[2]*j, -cube[0]*j, -cube[3]*j, -cube[1]*j, cube[4], cube[5], cube[6], cube[7]]
def l(cube): return [cube[0], cube[5]*k, cube[2], cube[1]*k, cube[4], cube[7]*k, cube[6], cube[3]*k]
def j_(cube): return [cube[0], -cube[3]*k, cube[2], -cube[7]*k, cube[4], -cube[1]*k, cube[6], -cube[5]*k]

def go(cube, visited, step):
        a = visited.copy()  # Create a copy of visited
        a.add(tuple(n(cube)))
        a.add(tuple(u(cube)))
        a.add(tuple(b(cube)))
        a.add(tuple(d(cube)))
        a.add(tuple(l(cube)))
        a.add(tuple(j_(cube)))

        if len(a) - len(visited) < 3: return
        for i in set(a) - set(visited):
                return go(i, visited + [i], step + 1)  # Pass a new visited list

icube = tuple([TRFaces(i,0,0) for i in range(8)])
go(icube, {icube}, 0)