import math

m = 1
k = 100
x02 = 0.5
dx02 = 0
v0 = 0
f = 10
t0 = 0
t1 = 10
x10 = 0.1
w0 = math.sqrt(k / m)
t_step = 0.01
w_step = w0 / 30

speed = {}
position = {}
w = {}
amplitud = {}

speed[0] = v0
position[0] = x02
w[0] = 0
amplitud[0] = 0

def function(x2,V,t,wt):
    return ( ( -k / m ) * ( x2 - x10 * math.cos( wt * t ) - x02 ) - ( f / m ) * V )

def eular():
    w_current = 0
    j = 0
    while w_current < (2 * w0):
        i = 1
        w[j] = w_current
        amplitud[j] = 0
        while i <= ( t1 - t0 ) / t_step:
            speed[i] = speed[i-1] + t_step * function(position[i - 1], speed[i - 1], t_step * i, w_current)
            position[i] = position[i-1] + t_step * speed[i]
            if(amplitud[j] < position[i]):
                amplitud[j] = position[i]
            i += 1
        w_current += w_step
        j += 1

def write_to_file(filename):
    file = open(filename, "a+")
    file.seek(0)
    file.truncate()
    i = 0
    while i <= 60:
        file.write(str(w[i]))
        file.write(",")
        file.write(str(amplitud[i]))
        file.write("\n")
        i+=1
    file.close()

eular()
write_to_file("lab4.csv")
