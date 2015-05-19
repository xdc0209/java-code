package com.xdc.basic.skills;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class ConfigUtil
{
    public static String getValue(String fileName, String key)
    {
        FileReader input = null;
        try
        {
            input = new FileReader(fileName);
            List<String> lines = IOUtils.readLines(input);
            IOUtils.closeQuietly(input);
            for (int i = 0; i < lines.size(); i++)
            {
                String line = lines.get(i);
                if (match(line, key))
                {
                    String[] split = line.split("=");
                    if (split != null && split.length == 2)
                    {
                        String matchKey = split[0];
                        String matchValue = split[1];
                        if (StringUtils.isNotBlank(matchKey) && StringUtils.isNotBlank(matchValue))
                        {
                            return matchValue.trim();
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            IOUtils.closeQuietly(input);
            System.err.println("Get value failed.");
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static void setValue(String fileName, String key, String value)
    {
        FileReader input = null;
        FileWriter output = null;
        try
        {
            input = new FileReader(fileName);
            List<String> lines = IOUtils.readLines(input);
            IOUtils.closeQuietly(input);

            for (int i = 0; i < lines.size(); i++)
            {
                String line = lines.get(i);
                if (match(line, key))
                {
                    String[] split = line.split("=");
                    if (split != null && split.length == 2)
                    {
                        String matchKey = split[0];
                        String matchValue = split[1];
                        if (StringUtils.isNotBlank(matchKey) && StringUtils.isNotBlank(matchValue))
                        {
                            lines.set(i, String.format("%s=%s", key, value));
                        }
                    }
                }
            }

            output = new FileWriter(fileName);
            IOUtils.writeLines(lines, null, output);
            IOUtils.closeQuietly(output);
        }
        catch (IOException e)
        {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
            System.err.println("Set value failed.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static boolean match(String line, String key)
    {
        // 空行
        if (StringUtils.isBlank(line))
        {
            return false;
        }

        // 注释行
        if (line.matches("^\\s*#.*$"))
        {
            return false;
        }

        // 匹配结果
        return line.matches(String.format("^\\s*%s\\s*=\\s*\\S+\\s*$", key));
    }
}