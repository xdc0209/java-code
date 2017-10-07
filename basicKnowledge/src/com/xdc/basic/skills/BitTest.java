package com.xdc.basic.skills;

public class BitTest
{
    public static void main(String[] args)
    {
        System.out.println(6 & 3); // 与。
        System.out.println(6 | 3); // 或。
        System.out.println(6 ^ 3); // 异或。
        System.out.println(~6); // 非。
        System.out.println(-13 << 2); // 算数左移，空位补0。每左移一位，相当于乘以2。
        System.out.println(-13 >> 2); // 算数右移，空位补符号位。每右移一位，相当于除以2并向下取整(负数也是一样的)。
        System.out.println(-13 >>> 2); // 逻辑右移，空位补0。
    }
}
