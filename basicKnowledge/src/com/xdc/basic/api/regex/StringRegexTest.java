package com.xdc.basic.api.regex;

import org.junit.Test;

public class StringRegexTest
{

    /**
     * 展示String中的正则表达式对分组的支持
     */
    @Test
    public void testGroup()
    {
        String str = "2014-12-21T14:25:14.397+08:00";
        String str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");

        System.out.println(str);
        System.out.println(str2);
    }

    @Test
    public void testMatches()
    {
        // 注意：matches的含义是整个字符串匹配
        // 更多信息参见 com.xdc.basic.api.regex.RegexTest.matcherFindAndMatches()
        String str = "User_001";
        System.out.println(str.matches("[a-zA-Z0-9_]+"));
    }

    @Test
    public void testMatchesNot()
    {
        // 还有个疑问：不知为何出现^(如将下面的改为"~!@#$^ ")，就会有问题, 感觉像是jdk的bug
        String str = "~!@#$ ";
        System.out.println(str.matches("[^a-zA-z0-9_]+"));
    }
}
