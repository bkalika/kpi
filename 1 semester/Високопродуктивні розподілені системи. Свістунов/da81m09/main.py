import math
import matplotlib.pyplot as plt

M1 = 1.
K1 = 100.
K2 = 100.
L1 = 0.1
X01 = 0.
V0X1 = 0.
DELTA_X10 = 0.05
X10 = 0.05
F1 = 0.
F2 = 0.
M2 = 1.
L2 = 0.1
X02 = 0.
V0X2 = 0.
DELTA_X20 = 0.05
X20 = 0.15
T_MIN = 0.
T_MAX = 10.
H = 0.05
F_MAX = 10.


def calc_f1(x1, x2, fr, t):
    return 1. / (M1 * F_MAX * math.cos(fr * t)) * (K2 * ((x2 - x1) - (X02 - X01)) - K1 * (x1 - X01))


def calc_f2(x1, x2):
    return 1. / M2 * (-K2 * ((x2 - x1) - (X02 - X01)))


def calc_next(yi, fi, h):
    return yi + h * fi


def add_to_result(x, v, t, result):
    result.append({'X': x, 'V': v, 'T': t})


def add_to_result_2(x_max, fr, result):
    result.append({'X_MAX': x_max, 'FR': fr})


def main():
    fr = 0.
    fr_h = 0.0005
    fr_max = 2. * X10

    result1 = []
    result2 = []
    x1List = []
    x2List = []
    wList = []

    while fr < fr_max:
        x1 = X10
        x2 = X20
        v1 = V0X1
        v2 = V0X2

        x1_arr = []
        x2_arr = []

        x1_arr.append(X10)
        x2_arr.append(X20)

        t = T_MIN + H

        while t < T_MAX:
            x1_next = x1 + H * v1
            x2_next = x2 + H * v2

            v1_next = v1 + H * calc_f1(x1_next, x2_next, fr, t)
            v2_next = v2 + H * calc_f2(x1_next, x2_next)

            x1 = x1_next
            x2 = x2_next
            v1 = v1_next
            v2 = v2_next

            x1_arr.append(x1)
            x2_arr.append(x2)

            t += H

        x1_max = max(x1_arr)
        x2_max = max(x2_arr)

        add_to_result_2(x1_max, fr, result1)
        add_to_result_2(x2_max, fr, result2)

        x1List.append(x1_max)
        x2List.append(x2_max)
        wList.append(fr)

        fr += fr_h

    print('First object:')
    for item in result1:
        print(item)
    print('Second object:')
    for item in result2:
        print(item)

    #plt.plot(x1List, wList)
    plt.ylabel('w0')
    plt.xlabel('x2')
    plt.plot(x2List, wList)
    #plt.plot(tList, x1List, label="Ox(t) - Oy(x1)")
    #plt.plot(tList, x2List, label="Ox(t) - Oy(x2)")
    #plt.plot(tList, v1List, label="Ox(t) - Oy(v1)")
    #plt.plot(tList, v2List, label="Ox(t) - Oy(v2)")

    #plt.title('Графік залежності зміщення (v) від часу (t)')
    plt.title('Графік амплітудної резонансної кривої')
    plt.legend()
    plt.show()


main()
