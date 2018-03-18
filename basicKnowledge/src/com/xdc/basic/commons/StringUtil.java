package com.xdc.basic.commons;

public class StringUtil
{
    public static boolean matches(String string, String regex)
    {
        // 字符串为空，或者正则表达式为空，则认为未匹配。
        if (string == null || regex == null)
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
        // 字符串为空，返回空数组。
        if (string == null)
        {
            return new String[0];
        }

        // 正则表达式为空，则认为未匹配，此时返回整个字符串。
        if (regex == null)
        {
            return new String[] { string };
        }

        return string.split(regex, -1);
    }
}
