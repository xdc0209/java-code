package com.xdc.basic.skills.findprime;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.skills.GetCurPath;

public class FindPrime
{
    public static void main(String[] args) throws InterruptedException
    {
        // if (args.length != 4)
        // {
        // System.out.println("参数错误！");
        // return;
        // }
        //
        // int beginNum = Integer.parseInt(args[0]);
        // int endNum = Integer.parseInt(args[1]);
        // int threadNum = Integer.parseInt(args[2]);
        // String outPath = args[3];

        String curPath = GetCurPath.getCurPath();

        int beginNum = 2;
        int endNum = 100000;
        int threadNum = 4;
        String outFile = "ans.txt";

        long startTime;
        long endTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<Element> ansArrayList = new ArrayList<Element>();

        startTime = System.currentTimeMillis(); // 获取开始时间

        System.out.println("区域划分：");
        int step = (endNum - beginNum) / threadNum + 1;
        for (int i = 0; i < threadNum; i++)
        {
            int begin = beginNum + i * step;
            if (begin >= endNum)
            {
                continue;
            }

            int end = beginNum + (i + 1) * step;
            if (end > endNum)
            {
                end = endNum;
            }

            // [begin,end)
            System.out.println("[" + begin + "," + end + ")");

            ansArrayList.add(new Element(begin, end));
        }

        for (Element element : ansArrayList)
        {
            RunnableImpl r = new RunnableImpl(element);
            Thread t = new Thread(r);
            t.start();
        }

        int aliveNum = 0;
        do
        {
            aliveNum = 0;
            for (Element element : ansArrayList)
            {
                if (element.isAlive)
                {
                    aliveNum++;
                }
            }
            System.out.println("子线程存活个数：" + aliveNum);
            Thread.sleep(2000);
        }
        while (aliveNum > 0);

        endTime = System.currentTimeMillis(); // 获取结束时间

        System.out.println("BeginDate :" + formatter.format(startTime));
        System.out.println("EndDate :" + formatter.format(endTime));
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");

        FileWriter wr = null;
        try
        {
            wr = new FileWriter(curPath + outFile);

            wr.write("BeginDate :" + formatter.format(startTime) + "\r\n");
            wr.write("EndDate :" + formatter.format(endTime) + "\r\n");
            wr.write("程序运行时间： " + (endTime - startTime) + "ms\r\n");

            wr.write("ans:\r\n");
            for (Element element : ansArrayList)
            {
                for (Integer integer : element.primeArrayList)
                {
                    wr.write(integer.toString() + "\r\n");
                }
            }

            wr.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(wr);
        }
    }
}
