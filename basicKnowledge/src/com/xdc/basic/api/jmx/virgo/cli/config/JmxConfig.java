package com.xdc.basic.api.jmx.virgo.cli.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.xdc.basic.skills.GetPath;

public class JmxConfig
{
    private static Properties p = null;

    private static Properties getProperties()
    {
        if (p == null)
        {
            try
            {
                p = new Properties();
                p.load(new FileInputStream(new File(GetPath.getRelativePath() + "jmx-access.properties")));
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
            catch (IOException e)
            {
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
