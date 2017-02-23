package com.xdc.basic.skills;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.api.jmx.virgo.cli.config.JmxConfig;

/**
 * 读取jar包内的配置文件
 */
public class GetResourceAsStream
{
    public static void main(String[] args) throws IOException
    {
        // Class.getResourceAsStream(String path):
        //
        // 其中:
        // path不以'/'开头时，默认是从此类所在的包下获取资源。
        // path以'/'开头时，则是从ClassPath根下获取资源。
        //
        // 注意：
        // 路径分隔符可以是斜杠(/)或是反斜杠(\)，或是混用，现代的java对这种路径要求没那么严格。
        // 但从ClassPath根下获取的话，开头一定要使用斜杠(/)，不要使用反斜杠(\)，否则找不到文件。
        InputStream in1 = JmxConfig.class.getResourceAsStream("jmx-access.properties");
        if (in1 == null)
        {
            System.err.println("ERROR: Config file [jmx-access.properties] not found.");
            System.exit(1);
        }
        System.out.println(IOUtils.toString(in1, "UTF-8"));

        // Class.getClassLoader().getResourceAsStream(String path):
        // 其中:
        // 从ClassPath根下获取资源。
        // 注意：
        // 路径分隔符可以是斜杠(/)或是反斜杠(\)，或是混用，现代的java对这种路径要求没那么严格。
        // 不能以'/'开头，否则找不到文件
        // 可以以'\'开头，也可不写。
        InputStream in2 = JmxConfig.class.getClassLoader()
                .getResourceAsStream("com/xdc\\basic\\api\\jmx\\virgo\\cli\\config\\jmx-access.properties");
        if (in2 == null)
        {
            System.err.println("ERROR: Config file [jmx-access.properties] not found.");
            System.exit(1);
        }
        System.out.println(IOUtils.toString(in2, "UTF-8"));
    }
}