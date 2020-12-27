import math

g = 9.8
l1 = 1
l2 = 1
m1 = 0.1
m2 = 0.1
k = 5
d = 0.2

startO1 = -0.08727
startO2 = 0

startV1 = 0
startV2 = 0

startT = 0
finishT = 30

step = 0.015

def getFirstSpeed(o1, o2):
    result = -g * math.sin(o1) / l1 + k * d * d * (math.sin(o2) - math.sin(o1)) * math.cos(o1) / (m1 * l1 * l1);
    return result

def getSecondSpeed(o1, o2):
    result = -g * math.sin(o2) / l2 - k * d * d * (math.sin(o2) - math.sin(o1)) * math.cos(o2) / (m2 * l2 * l2)
    return result

def main():
    o1 = startO1
    o2 = startO2
    v1 = startV1
    v2 = startV2

    o1List = [startO1]
    o2List = [startO2]
    v1List = [startV1]
    v2List = [startV2]

    t = startT

    while t < finishT:
        k1 = step / 2 * o1
        p1 = step / 2 * o2
        q1 = step / 2 * getFirstSpeed(o1, o2)
        r1 = step / 2 * getFirstSpeed(o1, o2)

        o1 = o1 + step * (v1 + k1)
        o2 = o2 + step * (v2 + p1)
        v1 = v1 + step * getFirstSpeed(o1 + q1, o2)
        v2 = v2 + step * getSecondSpeed(o1, o2 + r1)

        o1List.append(o1)
        o2List.append(o2)
        v1List.append(v1)
        v2List.append(v2)

        t += step


    file = open('output.csv', "a+")
    file.seek(0)
    file.truncate()
    t = 0
    for i in range(len(o1List)):
        line = ','
        line = line.join((str(t), str(v1List[i]), str(v2List[i]), str(o1List[i]), str(v2List[i])))
        file.write(line)
        file.write("\n")
        t += step
    file.close()


if __name__ == "__main__":
    main()
