package com.xdc.basic.skills;

public class IntOverflow
{
    public static boolean isAddOverflow(int x, int y)
    {
        int sum = x + y;
        return (x > 0 && y > 0 && sum <= 0) || (x < 0 && y < 0 && sum >= 0);
    }

    public static boolean isSubtractOverflow(int x, int y)
    {
        // 在数学中，绝对值相同但符号相反的两个数互为相反数，特别地，0的相反数是0。如果有一个数为n，那么-n表示n的相反数。
        // 但在计算机中，存在且唯一存在一个特殊情况，Integer.MIN_VALUE和-Integer.MIN_VALUE是相等的，导致这个的原因是在计算机中整数以补码形式表示的。
        if (y == Integer.MIN_VALUE)
        {
            return x >= 0;
        }
        else
        {
            return isAddOverflow(x, -y);
        }
    }

    public static boolean isMultiplyOverflow(int x, int y)
    {
        int product = x * y;
        return x != 0 && product / x != y;
    }

    public static boolean isDivideOverflow(int x, int y)
    {
        return y == 0;
    }

    public static void main(String[] args)
    {
        System.out.println("加法：");
        System.out.println(isAddOverflow(Integer.MIN_VALUE, -1));
        System.out.println(isAddOverflow(Integer.MIN_VALUE, 0));
        System.out.println(isAddOverflow(Integer.MIN_VALUE, 1));
        System.out.println(isAddOverflow(Integer.MAX_VALUE, -1));
        System.out.println(isAddOverflow(Integer.MAX_VALUE, 0));
        System.out.println(isAddOverflow(Integer.MAX_VALUE, 1));
        System.out.println();

        System.out.println("减法：");
        System.out.println(isSubtractOverflow(Integer.MIN_VALUE, -1));
        System.out.println(isSubtractOverflow(Integer.MIN_VALUE, 0));
        System.out.println(isSubtractOverflow(Integer.MIN_VALUE, 1));
        System.out.println(isSubtractOverflow(-1, Integer.MIN_VALUE));
        System.out.println(isSubtractOverflow(0, Integer.MIN_VALUE));
        System.out.println(isSubtractOverflow(1, Integer.MIN_VALUE));
        System.out.println(isSubtractOverflow(Integer.MAX_VALUE, -1));
        System.out.println(isSubtractOverflow(Integer.MAX_VALUE, 0));
        System.out.println(isSubtractOverflow(Integer.MAX_VALUE, 1));
        System.out.println(isSubtractOverflow(-1, Integer.MAX_VALUE));
        System.out.println(isSubtractOverflow(0, Integer.MAX_VALUE));
        System.out.println(isSubtractOverflow(1, Integer.MAX_VALUE));
        System.out.println();

        System.out.println("乘法：");
        System.out.println(isMultiplyOverflow(1, Integer.MAX_VALUE));
        System.out.println(isMultiplyOverflow(2, Integer.MAX_VALUE));
        System.out.println();

        System.out.println("除法：");
        System.out.println(isDivideOverflow(Integer.MAX_VALUE, 0));
        System.out.println(isDivideOverflow(Integer.MAX_VALUE, 1));
        System.out.println();
    }
}
