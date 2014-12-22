package com.xdc.basic.api.jmx.virgo.cli.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JmxConfig
{
    private static Properties p = null;

    private static Properties getProperties()
    {
        // Class.getResourceAsStream(String path) ：
        // path 不以’/'开头时默认是从此类所在的包下取资源，
        // 以’/'开头则是从ClassPath根下获取。
        InputStream in = JmxConfig.class.getResourceAsStream("jmx-access.properties");

        if (p == null)
        {
            try
            {
                p = new Properties();
                p.load(in);
            }
            catch (IOException e)
            {
                System.out.println("ERROR: Read config failed.");
                e.printStackTrace();
                System.exit(1);
            }
        }
        return p;
    }

    public static String getUrl()
    {
        return getProperties().getProperty("url");
    }

    public static String getIp()
    {
        return getProperties().getProperty("ip");
    }

    public static String getPort()
    {
        return getProperties().getProperty("port");
    }

    public static String getUser()
    {
        return getProperties().getProperty("user");
    }

    public static String getPassword()
    {
        return getProperties().getProperty("password");
    }
}
