package com.xdc.basic.api.google.guava.base;

import com.google.common.base.CharMatcher;

public class CharMatcherTest
{
    @SuppressWarnings({ "unused", "deprecation" })
    public static void main(String[] args)
    {
        String string = "1a2b3c4d";
        String noControl = CharMatcher.javaIsoControl().removeFrom(string); // 移除control字符
        String theDigits = CharMatcher.digit().retainFrom(string); // 只保留数字字符
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(string, ' '); // 去除两端的空格，并把中间的连续空格替换成单个空格
        String noDigits = CharMatcher.javaDigit().replaceFrom(string, "*"); // 用*号替换所有数字
        String lowerAndDigit = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(string); // 只保留数字和小写字母
    }
}
