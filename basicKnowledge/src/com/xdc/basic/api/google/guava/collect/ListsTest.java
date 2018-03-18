package com.xdc.basic.api.google.guava.collect;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;

public class ListsTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        List<String> list = Lists.newLinkedList();
        Set<String> set = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();
        String[] array = ObjectArrays.newArray(String.class, 10);

        Set<String> set2 = Sets.newHashSet("one", "two", "three");
        List<String> list2 = Lists.newArrayList("one", "two", "three");
        Map<String, String> map2 = ImmutableMap.of("ON", "TRUE", "OFF", "FALSE");
    }
}
