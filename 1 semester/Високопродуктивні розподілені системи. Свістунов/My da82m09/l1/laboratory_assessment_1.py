#!/usr/bin/env python

import matplotlib.pyplot as plt


m1 = 1.0
k1 = 100.0
l1 = 0.1
v0x1 = 0.0
dx10 = 0.05
x10 = 0.05
f1 = 0.0
m2 = 1.0
k2 = 100.0
l2 = 0.1
v0x2 = 0.0
dx20 = 0.05
x20 = 0.15
f2 = 0.0
t_min = 0.0
t_max = 10.0
n = 500


def speed_1(x1, x2, x02, x01):
    return (k2 / m1)*((x2 - x1) - (x02 - x01)) - (k1 / m1)*(x1 - x01)


def speed_2(x1, x2, x02, x01):
    return -(k2 / m2)*((x2 - x1) - (x02 - x01))


def euler():
    h_iter = (t_max - t_min) / n
    h = 0

    global vt
    global v1
    global v2
    global x_1
    global x_2

    vt = [0] * (n + 1)
    v1 = [0] * (n + 1)
    v2 = [0] * (n + 1)
    x_1 = [0] * (n + 1)
    x_2 = [0] * (n + 1)

    x01 = l1
    x02 = l2 + x01

    x_1[0] = x01 + dx10
    x_2[0] = x02 + dx20

    v1[0] = 0.0
    v2[0] = 0.0
    vt[0] = 0

    print('|    v1    |     v2     |     x1     |     x2   |   Time   |')
    print('------------------------------------------------------------')

    for i in range(n):
        v1[i + 1] = v1[i] + h_iter * speed_1(x_1[i], x_2[i], x01, x02)
        v2[i + 1] = v1[i] + h_iter * speed_2(x_1[i], x_2[i], x01, x02)
        x_1[i + 1] = x_1[i] + h_iter * v1[i]
        x_2[i + 1] = x_2[i] + h_iter * v2[i]
        print("| %.8f | %.8f | %.8f | %.8f | %.2f |" % (v1[i+1], v2[i+1], x_1[i+1], x_2[i+1], h))
        vt[i + 1] = h
        h += h_iter


euler()
print(vt)

plt.figure()
plt.title('V1(t)')
plt.xlabel('Time')
plt.ylabel('Velocity')
plt.plot(vt, v1, 'b')
plt.show()

plt.figure()
plt.title('V2(t)')
plt.xlabel('Time')
plt.ylabel('Velocity')
plt.plot(vt, v2, 'b')

plt.figure()
plt.title('X1(t)')
plt.xlabel('Time')
plt.ylabel('Distance')
plt.plot(vt, x_1, 'b')

plt.figure()
plt.title('X2(t)')
plt.xlabel('Time')
plt.ylabel('Distance')
plt.plot(vt, x_2, 'b')
plt.show()

