#include <stdio.h>

#define M1 (1.0)
#define K1 (10.0)
#define K2 (10.0)
#define X01 (0.05)
#define V0X1 (5.0)
#define D_X10 (0.05)
#define X10 (0.05)
#define F1 (0.0)
#define F2 (0.0)
#define M2 (1.0)
#define L2 (0.1)
#define X02 (0.0)
#define V0X2 (5.0)
#define D_X20 (0.05)
#define X20 (0.15)
#define T_MIN (0.0)
#define T_MAX (10.0)
#define H (0.05)

double f1(double x1, double x2)
{
    return (K2 * ((x2 - x1) - (X02 - X01)) - K1 * (x1 - X01)) / M1;
}

double f2(double x1, double x2)
{
    return (-K2 * ((x2 - x1) - (X02 - X01))) / M2;
}

double next(double yi, double fi, double h)
{
    return yi + h * fi;
}

void print_res(double t, double x1, double v1, double x2, double v2)
{
    printf("t = %.2f\n", t);
    printf("\tx = %.2f\tv = %.2f\n", x1, v1);
    printf("\tx = %.2f\tv = %.2f\n", x2, v2);
}

int main()
{
    double x1 = X10;
    double x2 = X20;
    double v1 = V0X1;
    double v2 = V0X2;
    print_res(T_MIN, x1, v1, x2, v2);
    double t = T_MIN + H;
    while (t <= T_MAX + 0.0001) {
        double x1_next = next(x1, v1, H);
        double x2_next = next(x2, v2, H);
        double v1_next = next(v1, f1(x1_next, x2_next), H);
        double v2_next = next(v2, f2(x1_next, x2_next), H);
        x1 = x1_next;
        x2 = x2_next;
        v1 = v1_next;
        v2 = v2_next;
        print_res(t, x1, v1, x2, v2);
        t += H;
    }
    return 0;
}
