package com.xdc.basic.skills.fastfindprime;

import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.commons.PathUtil;

public class FastFindPrime
{
    public static void main(String[] args)
    {
        // 求1--1000000之间的素数

        String curPath = PathUtil.getRelativePath();

        long startTime;
        long endTime;

        int startNum = 2;
        int endNum = 1000000;

        ArrayList<Integer> ansArrayList = new ArrayList<Integer>();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("1. 最原始的方法");
        startTime = System.currentTimeMillis();
        for (int i = startNum; i <= endNum; i++)
        {
            boolean isPrime = true;
            for (int j = 2; j <= i / 2; j++)
            {
                if (i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "1.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("2. 内层步进改为2，忽略偶数");
        startTime = System.currentTimeMillis();
        for (int i = startNum; i <= endNum; i++)
        {
            if (i > 2 && i % 2 == 0)
            {
                continue;
            }

            boolean isPrime = true;
            for (int j = 3; j <= i / 2; j = j + 2)
            {
                if (i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "2.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("3. 外层步进改为2，忽略偶数");
        startTime = System.currentTimeMillis();
        if (startNum == 2)
        {
            ansArrayList.add(2);
        }
        for (int i = startNum / 2 * 2 + 1; i <= endNum; i = i + 2)
        {
            boolean isPrime = true;
            for (int j = 2; j <= i / 2; j++)
            {
                if (i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "3.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("4. 内层外层步进同时改为2，忽略偶数");
        startTime = System.currentTimeMillis();
        if (startNum == 2)
        {
            ansArrayList.add(2);
        }
        for (int i = startNum / 2 * 2 + 1; i <= endNum; i++)
        {
            if (i > 2 && i % 2 == 0)
            {
                continue;
            }

            boolean isPrime = true;
            for (int j = 3; j <= i / 2; j = j + 2)
            {
                if (i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "4.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("5. 将上限由 i/2 缩小到 根号i");
        startTime = System.currentTimeMillis();
        for (int i = startNum; i <= endNum; i++)
        {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i) + 0.1; j++)
            {
                if (i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "5.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("6. 并将上限由 i/2 缩小到 根号i，内层步进改为2，忽略偶数");
        startTime = System.currentTimeMillis();
        for (int i = startNum; i <= endNum; i++)
        {
            if (i > 2 && i % 2 == 0)
            {
                continue;
            }

            boolean isPrime = true;
            for (int j = 3; j <= Math.sqrt(i) + 0.1; j = j + 2)
            {
                if (i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "6.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("7. 创新的想法:利用以求得的素数");
        startTime = System.currentTimeMillis();
        for (int i = startNum; i <= endNum; i++)
        {
            boolean isPrime = true;
            for (int j = 0; j < ansArrayList.size(); j++)
            {
                if (i % ansArrayList.get(j) == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "7.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        System.out.println("8. 创新的想法：利用以求得的素数，,并将上限由 i/2 缩小到 根号i");
        startTime = System.currentTimeMillis();
        for (int i = startNum; i <= endNum; i++)
        {
            boolean isPrime = true;
            for (int j = 0; j < ansArrayList.size() && ansArrayList.get(j) <= Math.sqrt(i) + 0.1; j++)
            {
                if (i % ansArrayList.get(j) == 0)
                {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime)
            {
                ansArrayList.add(i);
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("运行时间: " + (endTime - startTime) + "ms");
        System.out.println();

        writeToFile(curPath + "8.txt", ansArrayList);
        ansArrayList.clear();

        // ///////////////////////////////////////////////////////////////////
        // System.out.println("-------------------------");
        // System.out.println("额外测试：i=i+1 是否比 i++ 慢很多");
        //
        // System.out.println("i++");
        // startTime = System.currentTimeMillis();
        // for (int i = 0; i < 1000000000; i++)
        // {
        // }
        // endTime = System.currentTimeMillis();
        //
        // System.out.println("运行时间: " + (endTime - startTime) + "ms");
        // System.out.println();
        //
        // //
        // ///////////////////////////////////////////////////////////////////
        // System.out.println("i=i+2");
        // startTime = System.currentTimeMillis();
        // for (int i = 0; i < 1000000000; i = i + 2)
        // {
        // }
        // endTime = System.currentTimeMillis();
        //
        // System.out.println("运行时间: " + (endTime - startTime) + "ms");
        // System.out.println();

    }

    private static void writeToFile(String fileName, ArrayList<Integer> arrayList)
    {
        FileWriter fw = null;

        try
        {
            fw = new FileWriter(fileName);

            for (int i = 0; i < arrayList.size(); i++)
            {
                fw.write(arrayList.get(i).toString() + "\r\n");
            }

            fw.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(fw);
        }
    }
}
