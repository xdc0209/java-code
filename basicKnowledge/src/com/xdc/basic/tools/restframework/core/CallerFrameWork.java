package com.xdc.basic.tools.restframework.core;

import com.xdc.basic.tools.restclient.RestClient;
import com.xdc.basic.tools.restclient.RestClientFactory;

public class CallerFrameWork
{
    private static RestClient restClient = null;

    public static void init(String host)
    {
        if (restClient != null)
        {
            throw new IllegalStateException("The callerFrameWork has been initialed, destroy first.");
        }

        restClient = RestClientFactory.createHttpRestClient(host, null, null, null);
    }

    public static void destroy()
    {
        if (restClient == null)
        {
            throw new IllegalStateException("The callerFrameWork has been destroyed, init first.");
        }

        restClient = null;
    }

    public static RestClient getRestClient()
    {
        if (restClient == null)
        {
            throw new IllegalStateException("The callerFrameWork must init first.");
        }

        return restClient;
    }
}
