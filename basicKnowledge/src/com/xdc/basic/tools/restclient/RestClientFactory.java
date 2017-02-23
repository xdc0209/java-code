package com.xdc.basic.tools.restclient;

import com.xdc.basic.tools.restclient.constants.HttpProtocol;

public class RestClientFactory
{
    public static RestClient createHttpRestClient(String host, String port, String user, String password)
    {
        return createRestClient(HttpProtocol.HTTP, host, port, user, password);
    }

    public static RestClient createHttpsRestClient(String host, String port, String user, String password)
    {
        return createRestClient(HttpProtocol.HTTPS, host, port, user, password);
    }

    public static RestClient createRestClient(HttpProtocol protocol, String host, String port, String user,
            String password)
    {
        return new RestClient(protocol, host, port, user, password);
    }
}
