package com.xdc.basic.api.apache.commons.configuration.framwaork.config;

import com.xdc.basic.skills.GetPath;

public class PathConffig
{
    public static String getUserHome()
    {
        // 获取用户的家目录
        return System.getProperty("user.home");
    }

    public static String getUserDir()
    {
        // 获取用户的当前工作目录
        return System.getProperty("user.dir");
    }

    public static String getAppHome()
    {
        // 获取应用的家目录
        return EnvConfig.getenv("APP_HOME");
    }

    public static String getAppConfigDir()
    {
        // 获取应用存放配置文件的目录
        return GetPath.connect(getAppHome(), "config");
    }
}
