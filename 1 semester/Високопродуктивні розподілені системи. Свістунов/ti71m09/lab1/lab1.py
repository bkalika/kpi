#!/usr/bin/env python
import math
#import matplotlib.pyplot as plt

m_1 = 1.0
k_1 = 100.0
l_1 = 0.1
v_0x1 = 0.0
dx_10 = 0.05
x_10 = 0.05
f_1 = 0.0
m_2 = 1.0
k_2 = 100.0
l_2 = 0.1
v_0x2 = 0.0
dx_20 = 0.05
x_20 = 0.15
f_2 = 0.0
t_min = 0.0
t_max = 10.0
n = 500

def speed_1(x_1, x_2, x_02, x_01):
    res = (k_2/m_1)*((x_2 - x_1) - (x_02 - x_01)) - (k_1/m_1)*(x_1 - x_01)
    return res

def speed_2(x_1, x_2, x_02, x_01):
    res = -(k_2/m_2)*((x_2 - x_1) - (x_02 - x_01))
    return res

def euler():
    h_iter = (t_max - t_min) / n
    h = 0
    #global vt
    #global v_1
    #global v_2
    #global x_1
    #global x_2
    vt = [0] * (n + 1)
    v_1 = [0] * (n + 1)
    v_2 = [0] * (n + 1)
    x_1 = [0] * (n + 1)
    x_2 = [0] * (n + 1)

    x_01 = l_1
    x_02 = l_2 + x_01


    x_1[0] = x_01 + dx_10
    x_2[0] = x_02 + dx_20

    v_1[0] = 0.0
    v_2[0] = 0.0
    vt[0] = 0

    print("    v_1    |     v_2     |     x_1     |     x_2   | time")
    print("---------------------------------------------------------")

    for i in range(n):
        v_1[i+1] = v_1[i] + h_iter * speed_1(x_1[i], x_2[i], x_01, x_02)
        v_2[i+1] = v_1[i] + h_iter * speed_2(x_1[i], x_2[i], x_01, x_02)
        x_1[i+1] = x_1[i] + h_iter * v_1[i]
        x_2[i+1] = x_2[i] + h_iter * v_2[i]
        print("%.8f | %.8f | %.8f | %.8f | %.2f" % (v_1[i+1], v_2[i+1], x_1[i+1], x_2[i+1], h))
        vt[i+1] = h
        h += h_iter



   


euler()

#plt.figure()
#plt.title('V1(t)')
#plt.xlabel('Time')
#plt.ylabel('Velocity')
#plt.plot(vt, v_1, 'b')
#plt.show()

#plt.figure()
#plt.title('V2(t)')
#plt.xlabel('Time')
#plt.ylabel('Velocity')
#plt.plot(vt, v_2, 'b')

#plt.figure()
#plt.title('X1(t)')
#plt.xlabel('Time')
#plt.ylabel('Distance')
#plt.plot(vt, x_1, 'b')

#plt.figure()
#plt.title('X2(t)')
#plt.xlabel('Time')
#plt.ylabel('Distance')
#plt.plot(vt, x_2, 'b')
#plt.show()


