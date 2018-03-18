package com.xdc.basic.api.google.guava.collect;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * ImmutableSet、ImmutableMap与ImmutableList类似。
 */
public class ImmutableListTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        List<String> CONSTANT_LIST1 = new ImmutableList.Builder<String>().add("平均值").add("总值").add("最大值").add("最小值")
                .build();

        List<String> CONSTANT_LIST2 = ImmutableList.of("平均值", "总值", "最大值", "最小值");

        List<String> CONSTANT_LIST3 = ImmutableList.copyOf(Lists.newArrayList("平均值", "总值", "最大值", "最小值"));
    }
}
