package com.xdc.basic.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest
{
    public static void main(String[] args)
    {
        // 整数1 加或乘 整数2 用正则表达式进行输入验证

        @SuppressWarnings("resource")
        Scanner cin = new Scanner(System.in);

        String regex = "^\\s*(-?\\d+)\\s*([+*])\\s*(-?\\d+)\\s*$";
        Pattern pattern = Pattern.compile(regex);

        while (true)
        {
            String line = cin.nextLine();
            if (line.equals(""))
            {
                continue;
            }

            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
            {
                ArrayList<String> itemsArrayListList = new ArrayList<String>();
                // matcher.groupCount()方法返回 int 类型值，表示当前 Matcher 模式中捕获组的数量。本例中值为3。
                // 有一个特别的组——组 0，它表示整个表达式。这个组不包括在 groupCount 的报告范围内。
                // 因此下面的循环中，先加入整表达式，在依次放入：整数1， 加或乘， 整数2。
                for (int i = 0; i <= matcher.groupCount(); i++)
                {
                    String item = matcher.group(i);
                    itemsArrayListList.add(item);
                }
                for (String s : itemsArrayListList)
                {
                    System.out.println(s);
                }
            }
            else
            {
                System.out.println("表达式不合法!");
                continue;
            }
        }
    }
}
