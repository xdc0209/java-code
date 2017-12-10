package com.xdc.basic.api.restserver.jersey.core.config;

/**
 * 配置文件的读取不是本例的重点，使用此类模拟配置文件
 */
public class RestServerConfig
{
    public static String getRestServerIp()
    {
        return "0.0.0.0";
    }

    public static int getRestServerPort()
    {
        return 8080;
    }

    public static String getAccessKey()
    {
        return "miMNcYnPXFkiff1y";
    }

    public static String getSecretKey()
    {
        return "ZP7O+Agq5dCiZlPuvEwhqzpQGFIN0/S+hsGT8gtdZ5M=";
    }
}
