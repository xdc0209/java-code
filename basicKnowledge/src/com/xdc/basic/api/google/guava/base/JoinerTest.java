package com.xdc.basic.api.google.guava.base;

import com.google.common.base.Joiner;

public class JoinerTest
{
    public static void main(String[] args)
    {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String names = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(names);
    }
}
