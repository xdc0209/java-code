package com.xdc.basic.algorithm.sword4offer.question16;

public class Solution
{
    public static double Power(double base, int exponent)
    {
        if (Double.compare(base, 0) == 0 && exponent < 0)
        {
            throw new RuntimeException("Invalid input!");
        }

        int exponentAbs = exponent < 0 ? -exponent : exponent;

        double power = PowerWithNonNegativeExponent2(base, exponentAbs);

        if (exponent < 0)
        {
            power = 1 / power;
        }

        return power;
    }

    @SuppressWarnings("unused")
    private static double PowerWithNonNegativeExponent(double base, int exponent)
    {
        double power = 1.0;
        for (int i = 1; i <= exponent; i++)
        {
            power *= base;
        }
        return power;
    }

    /**
     * 利用公式高效地求数值的非负整数次方。
     * n为偶数：a^n = (a^(n/2))*(a^(n/2))
     * n为奇数：a^n = (a^((n-1)/2))*(a^((n-1)/2))*a
     */
    private static double PowerWithNonNegativeExponent2(double base, int exponent)
    {
        if (exponent == 0)
        {
            return 1;
        }

        if (exponent == 1)
        {
            return base;
        }

        double power = PowerWithNonNegativeExponent2(base, exponent >> 1);
        power *= power;
        if ((exponent & 1) == 1) // 判断exponent是否为奇数。
        {
            power *= base;
        }

        return power;
    }

    public static void main(String[] args)
    {
        System.out.println(Solution.Power(5, 3));
        System.out.println(Solution.Power(5, 2));
        System.out.println(Solution.Power(5, 1));
        System.out.println(Solution.Power(5, 0));
        System.out.println(Solution.Power(5, -1));
        System.out.println(Solution.Power(5, -2));
        System.out.println(Solution.Power(5, -3));
        System.out.println();

        System.out.println(Solution.Power(0, 3));
        System.out.println(Solution.Power(0, 2));
        System.out.println(Solution.Power(0, 1));
        System.out.println(Solution.Power(0, 0)); // 任何非零数的零次方都等于1(零的零次方无意义，但是各种各种计算器和库函数都返回1)。
        System.out.println(Solution.Power(0, -1)); // 0的负次幂非法，因为负次幂可理解为正次幂在取倒数。
        System.out.println(Solution.Power(0, -2));
        System.out.println(Solution.Power(0, -3));
        System.out.println();
    }
}
