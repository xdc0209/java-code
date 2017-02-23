package com.xdc.basic.api.regex;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表达式解析： 整数1 加或乘 整数2 用正则表达式进行输入验证
 * 
 * @author xdc
 * 
 */
public class ExpressionParse
{
    public static void main(String[] args)
    {
        @SuppressWarnings("resource")
        Scanner cin = new Scanner(System.in);

        // \s：代表空白，-？：代表0个或一个减号，\d:代表数字
        String regex = "^\\s*(-?\\d+)\\s*([+*])\\s*(-?\\d+)\\s*$";
        Pattern pattern = Pattern.compile(regex);

        while (true)
        {
            String line = cin.nextLine();
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }

            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
            {
                ArrayList<String> items = new ArrayList<String>();
                // 下面的循环中，先加入整表达式，在依次放入：整数1， 加或乘， 整数2。
                for (int i = 0; i <= matcher.groupCount(); i++)
                {
                    String item = matcher.group(i);
                    items.add(item);
                }
                System.out.println(items);
            }
            else
            {
                System.out.println("表达式不合法!");
            }
        }
    }
}
