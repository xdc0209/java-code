package com.xdc.basic.api.apache.commons.lang3.math;

import org.apache.commons.lang3.math.NumberUtils;

public class NumberUtilsTest
{
    public static void main(String[] args)
    {
        String str = "12345678901234567890";
        String str2 = "12345678901234567890.123";
        String str3 = "-12345678901234567890";
        String str4 = "0x12345678901234567890af";
        String str5 = "0x12345678901234567890aF";
        String str6 = "12345678901234567890L";
        String str7 = "12345678901234567890F";

        // Checks whether the String contains only digit characters, [0-9]+. 
        // Null and empty String will return false.
        System.out.println(NumberUtils.isDigits(str)); // true
        System.out.println(NumberUtils.isDigits(str2)); // false
        System.out.println(NumberUtils.isDigits(str3)); // false
        System.out.println(NumberUtils.isDigits(str4)); // false
        System.out.println(NumberUtils.isDigits(str5)); // false
        System.out.println(NumberUtils.isDigits(str6)); // false
        System.out.println(NumberUtils.isDigits(str7)); // false

        // Checks whether the String a valid Java number.
        // Valid numbers include hexadecimal marked with the 0x qualifier, scientific notation and numbers marked with a type qualifier (e.g. 123L).
        // Null and empty String will return false.
        System.out.println(NumberUtils.isNumber(str)); // true
        System.out.println(NumberUtils.isNumber(str2)); // true
        System.out.println(NumberUtils.isNumber(str3)); // true
        System.out.println(NumberUtils.isNumber(str4)); // true
        System.out.println(NumberUtils.isNumber(str5)); // true
        System.out.println(NumberUtils.isNumber(str6)); // true
        System.out.println(NumberUtils.isNumber(str7)); // true

        int[] array = { 1, 2, 5, 3, 4 };
        // 获取数组最大值
        System.out.println(NumberUtils.max(array));

        int a = 0;
        int b = 2;
        int c = 1;
        // 获取参数最大值
        System.out.println(NumberUtils.max(a, b, c));
    }
}
