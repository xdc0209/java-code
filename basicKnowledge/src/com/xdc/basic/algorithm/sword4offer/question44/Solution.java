package com.xdc.basic.algorithm.sword4offer.question44;

public class Solution
{
    public static int digitAtIndex(int index)
    {
        int digits = 1;

        while (index >= countOfIntegers(digits) * digits)
        {
            index -= countOfIntegers(digits) * digits;
            digits++;
        }

        return digitAtIndex(index, digits);
    }

    public static int countOfIntegers(int digits)
    {
        if (digits == 1)
        {
            return 10;
        }
        else
        {
            return 9 * ((int) Math.pow(10, digits - 1));
        }
    }

    public static int digitAtIndex(int index, int digits)
    {
        int number = beginNumber(digits) + index / digits;
        int indexFromRight = digits - index % digits;

        for (int i = 1; i < indexFromRight; i++)
        {
            number /= 10;
        }

        return number % 10;
    }

    public static int beginNumber(int digits)
    {
        if (digits == 1)
        {
            return 0;
        }
        else
        {
            return (int) Math.pow(10, digits - 1);
        }
    }

    public static int digitAtIndex2(int index)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; index >= sb.length(); i++)
        {
            sb.append(i);
        }

        return Integer.valueOf(String.valueOf(sb.charAt(index)));
    }

    public static void main(String[] args)
    {
        compareTest(0);
        compareTest(5);
        compareTest(9);
        compareTest(10);
        compareTest(12);
        compareTest(13);
        compareTest(19);
        compareTest(59);
        compareTest(99);
        compareTest(100);
        compareTest(190);
        compareTest(500);
        compareTest(999);
        compareTest(1000);
        compareTest(1001);
        compareTest(1002);
        compareTest(10000);
        compareTest(100000);
        compareTest(1000000);
        compareTest(1000001);

        for (int i = 0; i < 100000; i++)
        {
            if (!compareTest(i))
            {
                break;
            }
        }
    }

    public static boolean compareTest(int index)
    {
        int digitAtIndex = digitAtIndex(index);
        int digitAtIndex2 = digitAtIndex2(index);

        System.out.println(index + "：" + digitAtIndex + " " + digitAtIndex2 + " " + (digitAtIndex == digitAtIndex2));

        return digitAtIndex == digitAtIndex2;
    }
}
