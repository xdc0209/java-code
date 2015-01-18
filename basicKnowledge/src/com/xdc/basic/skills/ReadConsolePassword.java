package com.xdc.basic.skills;

import java.io.Console;

import org.apache.commons.lang3.ArrayUtils;

public class ReadConsolePassword
{
    /**
     * 在Java 字符终端上获取输入有三种方式：
     *
     * 1、java.lang.System.in (目前JDK版本均支持)
     * 
     * 2、java.util.Scanner (JDK版本>=1.5)
     * 
     * 3、java.io.Console(JDK版本>=1.6)，特色：能不回显密码字符
     * 
     * 由于Console访问操作系统平台上的控制台，因此这个测试用具只能在操作系统的字符控制台中运行，不能运行在 IDE 的控制台中。
     */
    public static String readConsolePassword(String tips)
    {
        Console console = System.console();
        if (console == null)
        {
            System.out.println("No console. Don't use this method in IDE.");
            System.exit(1);
        }

        char[] password = console.readPassword(tips);
        if (ArrayUtils.isEmpty(password))
        {
            System.out.println("Password could not be empty!");
            System.exit(1);
        }

        return String.valueOf(password);
    }
}
