package com.xdc.basic.skills;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ReadConsole
{
    /**
     * 由于Console访问操作系统平台上的控制台，因此这个测试用具只能在操作系统的字符控制台中运行，不能运行在 IDE 的控制台中。
     */
    @Test
    public void jdk1_6()
    {
        Console console = System.console();
        if (console == null)
        {
            System.err.println("No console.");
            System.exit(1);
        }

        while (true)
        {
            Pattern pattern = Pattern.compile(console.readLine("%nEnter your regex: "));
            Matcher matcher = pattern.matcher(console.readLine("Enter input string to search: "));
            boolean found = false;
            while (matcher.find())
            {
                console.format("I found the text '%s' starting at index %d " + "and ending at index %d.%n",
                        matcher.group(), matcher.start(), matcher.end());
                found = true;
            }
            if (!found)
            {
                console.format("No match found.%n");
            }
        }
    }

    @Test
    public void jdk1_5()
    {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.printf("%nEnter your regex: ");
            Pattern pattern = Pattern.compile(scanner.nextLine());
            System.out.printf("Enter input string to search: ");
            Matcher matcher = pattern.matcher(scanner.nextLine());
            boolean found = false;
            while (matcher.find())
            {
                System.out.printf("I found the text '%s' starting at index %d and ending at index %d.%n",
                        matcher.group(), matcher.start(), matcher.end());
                found = true;
            }
            if (!found)
            {
                System.out.printf("No match found.%n");
            }
        }
    }

    @Test
    public void jdk1_4() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
        while (true)
        {
            System.out.print("\nEnter your regex: ");
            Pattern pattern = Pattern.compile(br.readLine());
            System.out.print("Enter input string to search: ");
            Matcher matcher = pattern.matcher(br.readLine());
            boolean found = false;
            while (matcher.find())
            {
                System.out.println("I found the text '" + matcher.group() + "' starting at index " + matcher.start()
                        + " and ending at index " + matcher.end() + ".");
                found = true;
            }
            if (!found)
            {
                System.out.println("No match found.");
            }
        }
    }
}
