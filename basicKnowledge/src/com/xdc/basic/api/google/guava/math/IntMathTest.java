package com.xdc.basic.api.google.guava.math;

import com.google.common.math.IntMath;

/**
 * 参考：http://ifeve.com/google-guava-math
 */
public class IntMathTest
{
    public static void main(String[] args)
    {
        // 有溢出检查的运算。
        IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE); // throws ArithmeticException
        IntMath.checkedSubtract(Integer.MAX_VALUE, Integer.MIN_VALUE); // throws ArithmeticException
        IntMath.checkedMultiply(Integer.MAX_VALUE, Integer.MAX_VALUE); // throws ArithmeticException
        IntMath.checkedPow(Integer.MAX_VALUE, Integer.MAX_VALUE); // throws ArithmeticException

        // 最大公约数。
        IntMath.gcd(18, 24);

        // 是否2的幂。
        IntMath.isPowerOfTwo(8);
    }
}
