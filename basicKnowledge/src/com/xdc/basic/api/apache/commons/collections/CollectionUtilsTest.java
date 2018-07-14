package com.xdc.basic.api.apache.commons.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

public class CollectionUtilsTest
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        String[] arrayA = new String[] { "1", "2", "3", "3", "4", "5" };
        String[] arrayB = new String[] { "3", "4", "4", "5", "6", "7" };
        List<String> a = Arrays.asList(arrayA);
        List<String> b = Arrays.asList(arrayB);

        // 并集
        Collection<String> union = CollectionUtils.union(a, b);
        // 交集
        Collection<String> intersection = CollectionUtils.intersection(a, b);
        // 交集的补集
        Collection<String> disjunction = CollectionUtils.disjunction(a, b);
        // 集合相减
        Collection<String> subtract = CollectionUtils.subtract(a, b);

        // 排序
        Collections.sort((List<String>) union);
        Collections.sort((List<String>) intersection);
        Collections.sort((List<String>) disjunction);
        Collections.sort((List<String>) subtract);

        System.out.println("A: " + ArrayUtils.toString(a.toArray()));
        System.out.println("B: " + ArrayUtils.toString(b.toArray()));
        System.out.println("--------------------------------------------");
        System.out.println("Union(A, B): " + ArrayUtils.toString(union.toArray()));
        System.out.println("Intersection(A, B): " + ArrayUtils.toString(intersection.toArray()));
        System.out.println("Disjunction(A, B): " + ArrayUtils.toString(disjunction.toArray()));
        System.out.println("Subtract(A, B): " + ArrayUtils.toString(subtract.toArray()));

        // 查找
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("xudachao", 25, false));
        persons.add(new Person("gengxue", 24, false));
        Person person = (Person) CollectionUtils.find(persons,
                new BeanPropertyValueEqualsPredicate("name", "xudachao", true));
        System.out.println(person);
    }
}
