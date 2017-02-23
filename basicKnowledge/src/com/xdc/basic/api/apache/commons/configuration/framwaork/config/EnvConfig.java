package com.xdc.basic.api.apache.commons.configuration.framwaork.config;

import org.apache.commons.configuration.EnvironmentConfiguration;

public class EnvConfig
{
    private static EnvironmentConfiguration environmentConfiguration = new EnvironmentConfiguration();

    public static String getenv(String name)
    {
        return environmentConfiguration.getString(name);
    }
}
