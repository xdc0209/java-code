package com.xdc.basic.commons;

public class StringUtil
{
    public static boolean matches(String string, String regex)
    {
        if (string == null)
        {
            return false;
        }

        return string.matches(regex);
    }
}
