import time
import sys
import math

H = 0.001

K1 = 100
K2 = 100

M1 = 1
M2 = 1

l1 = 0.1
l2 = 0.1

x01 = l1
x02 = l2 + x01

F1 = 0
F2 = F1

def f1(t, x1, x2, u):
    return (K2 * ((x2 - x1) - (x02 - x01)) - K1 * (x1 - x01) - F1 * u) / M1


def f2(t, x1, x2, u):
    return (-K2 * ((x2 - x1) - (x02 - x01)) - F2 * u) / M2

def runge_kutta_local(calculation, ti, x1i, x2i, ui, func, h=H):
    def f(ti, x1i, x2i, ui):
        return ui

    if calculation == 0:
        k1 = h * f(ti, x1i, x2i, ui)
        l1 = h * func(ti, x1i, x2i, ui)

        k2 = h * f(ti + h / 2, x1i + k1 / 2, x2i, ui + l1 / 2)
        l2 = h * func(ti + h / 2, x1i + k1 / 2, x2i, ui + l1 / 2)

        k3 = h * f(ti + h / 2, x1i + k2 / 2, x2i, ui + l2 / 2)
        l3 = h * func(ti + h / 2, x1i + k2 / 2, x2i, ui + l2 / 2)

        k4 = h * f(ti + h, x1i + k3, x2i, ui + l3)
        l4 = h * func(ti + h, x1i + k3, x2i, ui + l3)

        previous_value = x1i

    elif calculation == 1:
        k1 = h * f(ti, x1i, x2i, ui)
        l1 = h * func(ti, x1i, x2i, ui)

        k2 = h * f(ti + h / 2, x1i, x2i + k1 / 2, ui + l1 / 2)
        l2 = h * func(ti + h / 2, x1i, x2i + k1 / 2, ui + l1 / 2)

        k3 = h * f(ti + h / 2, x1i, x2i + k2 / 2, ui + l2 / 2)
        l3 = h * func(ti + h / 2, x1i, x2i + k2 / 2, ui + l2 / 2)

        k4 = h * f(ti + h, x1i, x2i + k3, ui + l3)
        l4 = h * func(ti + h, x1i, x2i + k3, ui + l3)

        previous_value = x2i

    dx = (k1 + 2 * k2 + 2 * k3 + k4) / 6
    du = (l1 + 2 * l2 + 2 * l3 + l4) / 6

    x_ = previous_value + dx
    u_ = ui + du

    return x_, u_


def main():
    # Setting of started values
    t0 = 0

    x10 = 0.05
    x20 = 0.15

    # u = y' = dy/dx
    u01 = 0
    u02 = 0

    u1_list = []
    u1_list.append(u01)  # first value
    u2_list = []
    u2_list.append(u02)  # first value

    x1_list = []
    x1_list.append(x01 + 0.05)  # 0.1 + 0.05
    x2_list = []
    x2_list.append(x02 + 0.05)  # 0.1 + 0.1 + 0.05

    t_list = []
    t_list.append(t0)

    h = H
    i = 0
    t = t0
    t_end = 10
    while t < t_end:
        x1_new, u1_new = runge_kutta_local(0, t, x1_list[i], x2_list[i], u1_list[i], f1)
        x1_list.append(x1_new)
        u1_list.append(u1_new)

        x2_new, u2_new = runge_kutta_local(1, t, x1_new, x2_list[i], u2_list[i], f2)
        x2_list.append(x2_new)
        u2_list.append(u2_new)

        t += h
        t_list.append(t)

        i += 1

    count = i + 1
    for i in range(count):
        print("x1=" + str(x1_list[i]) + " v1=" + str(u1_list[i]) + " x2=" + str(x2_list[i]) + " v2=" + str(u2_list[i]))


if __name__ == "__main__":
    main()
