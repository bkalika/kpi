#!/usr/bin/env python
# -*- coding: utf-8 -*- 
import math 
#import matplotlib.pyplot as plt

M = 1 #масса тела
K = 100 #коэффициент жесткости
first_point = 0.5
first_speed = 0
F = 10 #коэффициент трения
T1 = 0 #начальный временной интервал
T2 = 10 #конечный временной интервал
x10 = 0.1 #амплитуда смещения левого конца пружины 
W = 0 #частота колебаний маятника и внешней силы 
step = 0.05 #шаг

speed = [first_speed]
coordinates = [first_point] 
force = [0] 
time=[T1]  

def getSpeed (x2, v, t): 

    return ( (-K / M) * (x2 - x10 * math.cos(W * t) - first_point) - (F / M) * v ) 

def main (): 

    file = open ('output.csv', "a+") 
    file.seek(0) 
    file.truncate() 

    i=1 

    while i <= (T2-T1)/step: 
        k1=getSpeed(coordinates[i-1], speed[i-1], step * i) 
        k2=getSpeed(coordinates[i-1]+step/2, speed[i-1]+step*k1/2, step * i) 
        k3=getSpeed(coordinates[i-1]+step/2, speed[i-1]+step*k2/2, step * i) 
        k4=getSpeed(coordinates[i-1]+step, speed[i-1]+step*k3, step * i) 
        speedValue = speed[i-1] + (step/6) * (k1+2*k2+2*k3+k4) 
        speed.append(speedValue)  
        x2 = coordinates[i-1] + step * speed[i-1] 
        coordinates.append(x2) 
        forceValue = F * speed[i-1] 
        #forceValue = F * math.cos(W * step * i) 
        force.append(forceValue) 
        time.append(T1 + i * step) 

        line = ',' 
        line = line.join((str(time[i]), str(coordinates[i]), str( 
            speed[i]), str(force[i]))) 
        print(line) 
        file.write(line) 
        i += 1 

    #plt.plot(time, force, label="Сила") 
    #plt.plot(time, speed, label="Скорость") 

    #plt.plot(time, force, label="Сила") 
    #plt.plot(time, coordinates, label="Позиция") 
	
    #plt.legend() 
    #plt.show()    

main() 