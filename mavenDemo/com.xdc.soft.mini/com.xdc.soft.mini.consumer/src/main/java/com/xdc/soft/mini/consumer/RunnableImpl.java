package com.xdc.soft.mini.consumer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdc.soft.mini.compute.Compute;

public class RunnableImpl implements Runnable
{
    private Compute compute;

    public RunnableImpl(Compute compute)
    {
        super();
        this.compute = compute;

    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        while (true)
        {

            ArrayList<String> buffer = intput();

            int x = Integer.valueOf(buffer.get(0));
            // String computeType = buffer.get(1);
            int y = Integer.valueOf(buffer.get(2));

            String result = compute.computeNmus(x, y);
            System.out.println("结果为：" + result + "\n");
        }
    }

    private ArrayList<String> intput()
    {
        @SuppressWarnings("resource")
        Scanner cin = new Scanner(System.in);

        // 检验输入合法性的正则表达式
        String regexCheck = "^\\s*-?\\d+\\s*[+*]\\s*-?\\d+\\s*$";
        Pattern patternCheck = Pattern.compile(regexCheck);

        // 分割输入字符串的正则表达式
        String regexSplit = "(-?\\d+|[+*])";
        Pattern patternSplit = Pattern.compile(regexSplit);

        while (true)
        {
            System.out.print("请输入表达式：");
            String line = cin.nextLine();
            // 如果只输入Enter，则继续
            if (line.equals(""))
            {
                continue;
            }

            // 判断输入串是否合法
            Matcher matcherCheck = patternCheck.matcher(line);
            if (matcherCheck.find() == false)
            {
                System.out.println("表达式不合法!\n");
                continue;
            }

            // 分割输入串
            Matcher matcherSplit = patternSplit.matcher(line);
            ArrayList<String> itemsArrayListList = new ArrayList<String>();
            while (matcherSplit.find())
            {
                String item = matcherSplit.group();
                itemsArrayListList.add(item);
            }
            return itemsArrayListList;
        }
    }
}
