package com.xdc.basic.commons;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest
{
    @Test
    public void testMatches()
    {
        Assert.assertEquals(false, StringUtil.matches(null, "^$"));
        Assert.assertEquals(false, StringUtil.matches(null, "^[a-zA-Z]+$"));

        Assert.assertEquals(true, StringUtil.matches("", "^$"));
        Assert.assertEquals(false, StringUtil.matches("", "^[a-zA-Z]+$"));

        Assert.assertEquals(false, StringUtil.matches("    ", "^$"));
        Assert.assertEquals(false, StringUtil.matches("    ", "^[a-zA-Z]+$"));

        Assert.assertEquals(false, StringUtil.matches("hehe", "^$"));
        Assert.assertEquals(true, StringUtil.matches("hehe", "^[a-zA-Z]+$"));
    }

    @Test
    public void testSplit()
    {
        // 方法java.lang.String.split(regex)的有点诡异，下面的程序用于备忘。java.lang.String.split(regex)等价于java.lang.String.split(regex, 0)。

        // java.lang.String.split(regex, 0)：按照正则表达式分割，如果未匹配，则返回整个字符串。除了匹配到的分隔符，其他任何字符都会被保留，包括空白符(空格符、制表符)。因为limit为0，所以末尾空串(长度为0的串，与空白串不同)会被删除。
        split("", ",", 0); // 未匹配，返回全部。
        split(",", ",", 0); // 末尾空串被删除。
        split("1", ",", 0);
        split("1,2", ",", 0);
        split("1,", ",", 0);
        split(",2", ",", 0);
        split(",, , ,,,1,, , ,,,3,, , ,,,", ",", 0);
        split(" , ,,, , ,1 , ,,, , ,3 , ,,, , ,", ",", 0);
        System.out.println();

        // java.lang.String.split(regex, -1)：按照正则表达式分割，如果未匹配，则返回整个字符串。除了匹配到的分隔符，其他任何字符都会被保留，包括空白符(空格符、制表符)。因为limit为-1，所以末尾空串(长度为0的串，与空白串不同)会被保留。
        split("", ",", -1); // 未匹配，返回全部。
        split(",", ",", -1); // 全部保留。
        split("1", ",", -1);
        split("1,2", ",", -1);
        split("1,", ",", -1);
        split(",2", ",", -1);
        split(",, , ,,,1,, , ,,,3,, , ,,,", ",", -1);
        split(" , ,,, , ,1 , ,,, , ,3 , ,,, , ,", ",", -1);
        System.out.println();
    }

    public void split(String string, String regex, int limit)
    {
        String[] strings = string.split(regex, limit);

        System.out.println(String.format("string:[%s],regex:[%s],limit:[%d]-->size:[%d],strings:[%s]", string, regex,
                limit, strings.length, Arrays.toString(strings)));
    }
}
