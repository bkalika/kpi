f_koef = 0.2

m1 = 1
k1 = 100
l1 = 0.1
x01 = 0
start_v1 = 0
delta_x1 = 0.05
start_x1 = 0.05
f1 = f_koef * 9.8 * m1

m2 = 1
k2 = 100
l2 = 0.1
x02 = 0
start_v2 = 0
delta_x2 = 0.05
start_x2 = 0.15
f2 = f_koef * 9.8 * m2

startT = 0
finishT = 10

step = 0.002

isFirstBodyGoLeft = False
isSecondBodyGoLeft = True


def getFirstSpeed(x1, x2):
    global isFirstBodyGoLeft
    friction = 0

    if isFirstBodyGoLeft:
        friction = f1
    else:
        friction = -f1

    result = (k2 * ((x2 - x1) - (x02 - x01)) -
              k1 * (x1 - x01) + friction) / m1

    if ((result > x1 and isFirstBodyGoLeft) or (result < x1 or not isFirstBodyGoLeft)):
        isFirstBodyGoLeft = not isFirstBodyGoLeft

    return result


def getSecondSpeed(x1, x2):
    global isSecondBodyGoLeft
    friction = 0

    if isSecondBodyGoLeft:
        friction = f2
    else:
        friction = -f2

    result = (-k2 * ((x2 - x1) - (x02 - x01)) + friction) / m2

    if ((result > x2 and isSecondBodyGoLeft) or (result < x2 or not isSecondBodyGoLeft)):
        isSecondBodyGoLeft = not isSecondBodyGoLeft

    return result


def main():
    x1 = start_x1
    x2 = start_x2
    v1 = start_v1
    v2 = start_v2

    x1List = [start_x1]
    x2List = [start_x2]
    v1List = [start_v1]
    v2List = [start_v2]
    tList = [startT]

    t = startT

    while t < finishT:
        x1 = x1 + step * v1
        x2 = x2 + step * v2
        v1 = v1 + step * getFirstSpeed(x1, x2)
        v2 = v2 + step * getSecondSpeed(x1, x2)

        x1List.append(x1)
        x2List.append(x2)
        v1List.append(v1)
        v2List.append(v2)

        t += step
        tList.append(t)

    print("t, v1, v2, x1, x2")
    t = 0
    for i in range(len(x1List)):
        line = ','
        line = line.join((str(t), str(v1List[i]), str(
            v2List[i]), str(x1List[i]), str(x2List[i])))
        print(line)
        t += step


if __name__ == "__main__":
    print("lab1")
    main()
