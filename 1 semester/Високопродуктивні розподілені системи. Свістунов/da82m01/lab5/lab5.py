import math

MASS = 1                        # mass
K = 100                         # spring hardness
INIT_POS = 0.5                  # body init position
INIT_SPEED = 0                  # body init speed
T_START = 0                     # base body shift
FRICTION = 10                   # friction 
T_FINISH = 10                   # simulation end time
X_10 = 0.1                      # spring amplitude 
W = 2*math.sqrt(K/MASS)           # frequency of external force
RESISTANCE = (FRICTION / MASS)  # resistant force
STEP = 0.01                     # selected time interval

#resulting arrays
FORCE_ARR = {}
SPEED_ARR = {}
POSITION_ARR = {}

def func (x2, V, t):
    return ((-K / MASS) * (x2 - X_10 * math.cos(W * t) - INIT_POS) - RESISTANCE * V)

def calculate():
    SPEED_ARR[0] = INIT_SPEED
    POSITION_ARR[0] = INIT_POS
    FORCE_ARR[0] = 0
    i=1
    while i <= (T_FINISH-T_START)/STEP:
        SPEED_ARR[i] = SPEED_ARR[i-1] + STEP * func(POSITION_ARR[i-1], SPEED_ARR[i-1], STEP * i)
        POSITION_ARR[i] = POSITION_ARR[i-1] + STEP * SPEED_ARR[i]
        FORCE_ARR[i] = MASS * X_10 * math.cos(W * STEP * i)
        i += 1

def export ():
    file = open ('lab1.csv', "a+")
    file.seek(0)
    file.truncate()
    i = 0
    while i <= ((T_FINISH - T_START) / STEP):
        t_curr = T_START + STEP * i
        line = ','
        line = line.join((str(t_curr), str(SPEED_ARR[i]), str(POSITION_ARR[i]), str(FORCE_ARR[i])))
        file.write(line)
        file.write("\n")
        i+=1
    file.close()

calculate()
export()

