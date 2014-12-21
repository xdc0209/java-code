package com.xdc.basic.api.regex;

import org.junit.Test;

public class StringRegexTest
{
    @Test
    /**
     * 展示String中的正则表达式对分组的支持
     */
    public void testGroup()
    {
        String str = "2014-12-21T14:25:14.397+08:00";
        String str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");

        System.out.println(str);
        System.out.println(str2);
    }
}
