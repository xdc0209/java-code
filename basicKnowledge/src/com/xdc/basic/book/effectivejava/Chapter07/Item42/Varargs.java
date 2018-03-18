package com.xdc.basic.book.effectivejava.Chapter07.Item42;

// Sample uses of varargs
public class Varargs
{

    // Simple use of varargs - Page 197
    static int sum(int... args)
    {
        int sum = 0;
        for (int arg : args)
        {
            sum += arg;
        }
        return sum;
    }

    // The WRONG way to use varargs to pass one or more arguments! - Page 197
    // static int min(int... args) {
    // if (args.length == 0)
    // throw new IllegalArgumentException("Too few arguments");
    // int min = args[0];
    // for (int i = 1; i < args.length; i++)
    // if (args[i] < min)
    // min = args[i];
    // return min;
    // }

    // The right way to use varargs to pass one or more arguments - Page 198
    static int min(int firstArg, int... remainingArgs)
    {
        int min = firstArg;
        for (int arg : remainingArgs)
        {
            if (arg < min)
            {
                min = arg;
            }
        }
        return min;
    }

    public static void main(String[] args)
    {
        System.out.println(sum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println(min(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
}
