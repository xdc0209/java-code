package com.xdc.basic.commons.collection;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

class ListUtilTest
{
    @Test
    void test()
    {
        List<String> arrayToList1 = ListUtil.arrayToList((String[]) null);
        System.out.println(arrayToList1);

        List<String> arrayToList2 = ListUtil.arrayToList(new String[0]);
        System.out.println(arrayToList2);

        List<String> arrayToList3 = ListUtil.arrayToList(new String[] { "abc", "def" });
        System.out.println(arrayToList3);

        String[] listToArray1 = ListUtil.listToArray(null, String.class);
        System.out.println(Arrays.toString(listToArray1));

        String[] listToArray2 = ListUtil.listToArray(Lists.newArrayList(), String.class);
        System.out.println(Arrays.toString(listToArray2));

        String[] listToArray3 = ListUtil.listToArray(Lists.newArrayList("abc", "def"), String.class);
        System.out.println(Arrays.toString(listToArray3));
    }
}
