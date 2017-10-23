package com.xdc.basic.tools.restclient.constants;

import org.apache.commons.lang3.StringUtils;

public enum HttpProtocol
{
    HTTP("http", "80"), HTTPS("https", "443");
    String name;
    String defaultPort;

    private HttpProtocol(String name, String defaultPort)
    {
        this.name = name;
        this.defaultPort = defaultPort;
    }

    public String getName()
    {
        return name;
    }

    public String getDefaultPort()
    {
        return defaultPort;
    }

    HttpProtocol getProtocol(String name)
    {
        for (HttpProtocol protocol : HttpProtocol.values())
        {
            if (StringUtils.equalsIgnoreCase(protocol.getName(), name))
            {
                return protocol;
            }
        }

        // 其他不合法的字符串返回http
        return HttpProtocol.HTTP;
    }
}
