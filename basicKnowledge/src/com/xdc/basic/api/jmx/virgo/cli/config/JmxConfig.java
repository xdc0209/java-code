package com.xdc.basic.api.jmx.virgo.cli.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JmxConfig
{
    private static Properties p = null;

    private static Properties getProperties()
    {
        if (p == null)
        {
            InputStream in = JmxConfig.class.getResourceAsStream("jmx-access.properties");
            if (in == null)
            {
                System.err.println("ERROR: Config file [jmx-access.properties] not found.");
                System.exit(1);
            }

            try
            {
                p = new Properties();
                p.load(in);
            }
            catch (IOException e)
            {
                System.err.println("ERROR: Read config failed.");
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
