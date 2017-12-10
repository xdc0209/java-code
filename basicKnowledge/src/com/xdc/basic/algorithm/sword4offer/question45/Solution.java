package com.xdc.basic.algorithm.sword4offer.question45;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution
{
    public static String PrintMinNumber(int[] numbers)
    {
        List<String> stringNumbers = new ArrayList<String>();
        for (int number : numbers)
        {
            stringNumbers.add(String.valueOf(number));
        }

        Collections.sort(stringNumbers, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                if (o1 == null && o2 == null)
                {
                    return 0;
                }

                return (o1 + o2).compareTo(o2 + o1);
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String stringNumber : stringNumbers)
        {
            sb.append(stringNumber);
        }

        return sb.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(PrintMinNumber(newArray(12, 121)));
        System.out.println(PrintMinNumber(newArray(1, 11, 111)));
        System.out.println(PrintMinNumber(newArray(3, 32, 321)));
        System.out.println(PrintMinNumber(newArray(3, 32, 321, 1, 14, 142, 13, 122)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
