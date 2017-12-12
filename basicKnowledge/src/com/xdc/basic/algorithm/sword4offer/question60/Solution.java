package com.xdc.basic.algorithm.sword4offer.question60;

public class Solution
{
    private static final int MAX_VALUE = 6;

    public static void PrintProbability_Solution1(int number)
    {
        if (number < 1)
        {
            return;
        }

        int maxSum = number * MAX_VALUE;
        int[] probabilities = new int[maxSum - number + 1];
        for (int i = number; i <= maxSum; ++i)
        {
            probabilities[i - number] = 0;
        }

        Probability(number, probabilities);

        int total = (int) Math.pow(MAX_VALUE, number);
        for (int i = number; i <= maxSum; i++)
        {
            System.out.printf("%3d: %d/%d\n", i, probabilities[i - number], total);
        }
    }

    private static void Probability(int number, int[] probabilities)
    {
        for (int i = 1; i <= MAX_VALUE; i++)
        {
            Probability(number, number, i, probabilities);
        }
    }

    private static void Probability(int original, int current, int sum, int[] probabilities)
    {
        if (current == 1)
        {
            probabilities[sum - original]++;
            return;
        }

        for (int i = 1; i <= MAX_VALUE; ++i)
        {
            Probability(original, current - 1, i + sum, probabilities);
        }
    }

    public static void PrintProbability_Solution2(int number)
    {
        if (number < 1)
        {
            return;
        }

        int[][] probabilities = new int[2][];
        probabilities[0] = new int[MAX_VALUE * number + 1];
        probabilities[1] = new int[MAX_VALUE * number + 1];
        for (int i = 0; i < MAX_VALUE * number + 1; i++)
        {
            probabilities[0][i] = 0;
            probabilities[1][i] = 0;
        }

        int cur = 0;
        int next = 1;
        for (int i = 1; i <= MAX_VALUE; i++)
        {
            probabilities[cur][i] = 1;
        }
        for (int k = 2; k <= number; k++)
        {
            for (int i = 0; i < k; i++)
            {
                probabilities[next][i] = 0;
            }

            for (int i = k; i <= MAX_VALUE * k; i++)
            {
                probabilities[next][i] = 0;
                for (int j = 1; j <= MAX_VALUE && i - j >= 0; j++)
                {
                    probabilities[next][i] += probabilities[cur][i - j];
                }
            }

            cur = 1 - cur;
            next = 1 - next;
        }

        int total = (int) Math.pow(MAX_VALUE, number);
        for (int i = number; i <= MAX_VALUE * number; ++i)
        {
            System.out.printf("%3d: %d/%d\n", i, probabilities[cur][i], total);
        }
    }

    public static void main(String[] args)
    {
        PrintProbability_Solution1(2);
        System.out.println();

        PrintProbability_Solution2(2);
        System.out.println();
    }
}
