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
}
