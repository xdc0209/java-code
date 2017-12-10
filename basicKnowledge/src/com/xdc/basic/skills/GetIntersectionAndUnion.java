package com.xdc.basic.skills;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 集合的交集与并集
 * 
 * @author xdc
 * 
 */
public class GetIntersectionAndUnion
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        List<String> list1 = new ArrayList<String>();
        list1.add("a");
        list1.add("b");
        list1.add("b");

        List<String> list2 = new ArrayList<String>();
        list2.add("a");
        list2.add("b");
        list2.add("c");

        // 集合的交集: 在list2中保留list1中存在的元素
        list2.retainAll(list1);
        System.out.println(list2);

        // 在list2中去除list1中存在的元素
        list2.removeAll(list1);
        // 集合的并集
        list2.addAll(list1);
        System.out.println(list2);

        // apache 交集
        List<String> list3 = (List<String>) CollectionUtils.intersection(list1, list2);
        System.out.println(list3);

        // apcahe 并集
        List<String> list4 = (List<String>) CollectionUtils.union(list1, list2);
        System.out.println(list4);
    }

}
