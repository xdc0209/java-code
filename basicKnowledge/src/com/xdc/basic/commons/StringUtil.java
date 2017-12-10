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

    /**
     * 分割字符串，分割时保留空串(长度为0的串，与空白串不同)。
     * 如果想在分割时过滤所有空串，请使用String[] org.apache.commons.lang3.StringUtils.split(String str, String separatorChars)。
     */
    public static String[] split(String string, String regex)
    {
        if (string == null)
        {
            return new String[0];
        }

        return string.split(regex, -1);
    }
}
