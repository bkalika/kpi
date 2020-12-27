#!/usr/bin/env python

f_coefficient = 0.2

m_1 = 1
k_1 = 100
l_1 = 0.1
x_01 = 0
start_v1 = 0
delta_x1 = 0.05
start_x1 = 0.05
f_1 = f_coefficient * 9.8 * m_1

m_2 = 1
k_2 = 100
l_2 = 0.1
x_02 = 0
start_v2 = 0
delta_x2 = 0.05
start_x2 = 0.15
f_2 = f_coefficient * 9.8 * m_2

start_t = 0
finish_t = 10

step = 0.002

is_first_body_go_left = False
is_second_body_go_left = True


t_min = 0.0
t_max = 10.0
n = 500


def get_first_speed(x1, x2):
    global is_first_body_go_left
    # friction = 0

    if is_first_body_go_left:
        friction = f_1
    else:
        friction = -f_1

    result = (k_2 * ((x2 - x1) - (x_02 - x_01)) -
              k_1 * (x1 - x_01) + friction) / m_1

    if ((result > x1 and is_first_body_go_left)
            or (result < x1 or not is_first_body_go_left)):
        is_first_body_go_left = not is_first_body_go_left

    return result


def get_second_speed(x1, x2):
    global is_second_body_go_left
    # friction = 0

    if is_second_body_go_left:
        friction = f_2
    else:
        friction = -f_2

    result = (-k_2 * ((x2 - x1) - (x_02 - x_01)) + friction) / m_2

    if ((result > x2 and is_second_body_go_left)
            or (result < x2 or not is_second_body_go_left)):
        is_second_body_go_left = not is_second_body_go_left

    return result


def main():
    x1 = start_x1
    x2 = start_x2
    v1 = start_v1
    v2 = start_v2

    x1_list = [start_x1]
    x2_list = [start_x2]
    v1_list = [start_v1]
    v2_list = [start_v2]
    t_list = [start_t]

    t = start_t

    while t < finish_t:
        x1 = x1 + step * v1
        x2 = x2 + step * v2
        v1 = v1 + step * get_first_speed(x1, x2)
        v2 = v2 + step * get_second_speed(x1, x2)

        x1_list.append(x1)
        x2_list.append(x2)
        v1_list.append(v1)
        v2_list.append(v2)

        t += step
        t_list.append(t)
    t = 0

    print("t, v1, v2, x1, x2")
    with open("out_data.txt", "w") as file:
        for i in range(len(x1_list)):
            line = ', '
            line = line.join((str(t), str(v1_list[i]), str(
                v2_list[i]), str(x1_list[i]), str(x2_list[i]), '\n'))
            file.write(line)
            print(line)
            t += step


if __name__ == "__main__":
    print("laboratory assessment 3")
    main()
