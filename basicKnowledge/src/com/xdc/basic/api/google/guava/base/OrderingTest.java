package com.xdc.basic.api.google.guava.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

/**
 * 参考：http://www.baeldung.com/guava-order
 */
public class OrderingTest
{
    @Test
    public void test1()
    {
        // nulls first
        List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsFirst());

        Assert.assertNull(toSort.get(0));
    }

    @Test
    public void test2()
    {
        // nulls last
        List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsLast());

        Assert.assertNull(toSort.get(toSort.size() - 1));
    }

    @Test
    public void test3()
    {
        // natural ordering
        List<Integer> toSort = Arrays.asList(3, 5, 4, 1, 2);
        Collections.sort(toSort, Ordering.natural());

        Assert.assertTrue(Ordering.natural().isOrdered(toSort));
    }

    @Test
    public void test4()
    {
        // chaining 2 orderings
        List<Integer> toSort = Arrays.asList(3, 5, 4, 1, 2);
        Collections.sort(toSort, Ordering.natural().reverse());

        Assert.assertEquals(toSort.get(0), Integer.valueOf(5));
    }

    @Test
    public void test5()
    {
        // reverse an ordering
        List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsLast().reverse());

        Assert.assertEquals(toSort.get(0), null);
    }

    class OrderingByLenght extends Ordering<String>
    {
        @Override
        public int compare(String s1, String s2)
        {
            return Ints.compare(s1.length(), s2.length());
        }
    }

    @Test
    public void test6()
    {
        // custom order – Strings by length
        List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        Ordering<String> orderingByLenght = new OrderingByLenght();
        Collections.sort(toSort, orderingByLenght);

        // checking explicit order
        Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "zz", "aa", "ccc"));
        Assert.assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public void test7()
    {
        // checking string ordering
        List<Integer> toSort = Arrays.asList(3, 5, 4, 2, 1, 2);
        Collections.sort(toSort, Ordering.natural());

        Assert.assertFalse(Ordering.natural().isStrictlyOrdered(toSort));
    }

    @Test
    public void test8()
    {
        // secondary ordering
        List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        Ordering<String> byLength = new OrderingByLenght();
        Collections.sort(toSort, byLength.compound(Ordering.natural()));

        Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "zz", "ccc"));
        Assert.assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public void test9()
    {
        // complex custom ordering example – with chaining
        List<String> toSort = Arrays.asList("zz", "aa", null, "b", "ccc");
        Collections.sort(toSort, new OrderingByLenght().reverse().compound(Ordering.natural()).nullsLast());
    }

    @Test
    public void test10()
    {
        // sort using toString representation
        List<Integer> toSort = Arrays.asList(1, 2, 11);
        Collections.sort(toSort, Ordering.usingToString());

        Ordering<Integer> expectedOrder = Ordering.explicit(Lists.newArrayList(1, 11, 2));
        Assert.assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public void test11()
    {
        // find min/max without having to sort (faster)
        List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        int found = Ordering.usingToString().min(toSort);

        Assert.assertEquals(found, 1);
    }

    @Test
    public void test12()
    {
        // creating a sorted copy of the list from an ordering
        List<String> toSort = Arrays.asList("aa", "b", "ccc");
        List<String> sortedCopy = new OrderingByLenght().sortedCopy(toSort);

        Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "ccc"));
        Assert.assertFalse(expectedOrder.isOrdered(toSort));
        Assert.assertTrue(expectedOrder.isOrdered(sortedCopy));
    }

    @Test
    public void test13()
    {
        // creating a sorted partial copy – the least few elements
        List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        List<Integer> leastOf = Ordering.natural().leastOf(toSort, 3);

        List<Integer> expected = Lists.newArrayList(1, 2, 8);
        Assert.assertEquals(expected, leastOf);
    }

    @Test
    public void test14()
    {
        // ordering via intermediary Function
        List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
        List<Integer> sortedCopy = ordering.sortedCopy(toSort);

        List<Integer> expected = Lists.newArrayList(1, 100, 11, 14, 2, 8);
        Assert.assertEquals(expected, sortedCopy);
    }
}
