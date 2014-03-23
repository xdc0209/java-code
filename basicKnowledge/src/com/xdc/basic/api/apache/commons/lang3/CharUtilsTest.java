package com.xdc.basic.api.apache.commons.lang3;

import org.apache.commons.lang3.CharUtils;

public class CharUtilsTest
{
    public static void main(String[] args)
    {
        char ch = 'a';

        // Checks whether the character is ASCII 7 bit.
        CharUtils.isAscii(ch);

        // 字母
        CharUtils.isAsciiAlpha(ch);
        CharUtils.isAsciiAlphaLower(ch);
        CharUtils.isAsciiAlphaUpper(ch);

        // 数字
        CharUtils.isAsciiNumeric(ch);

        // 字母或数字
        CharUtils.isAsciiAlphanumeric(ch);
    }
}
