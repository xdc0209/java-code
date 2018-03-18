package com.xdc.basic.api.google.guava.base;

import java.util.List;

import com.google.common.base.Splitter;

public class SplitterTest
{
    public static void main(String[] args)
    {
        Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();
        List<String> strings = splitter.splitToList("foo,bar,,   qux");
        System.out.println(strings);
    }
}
