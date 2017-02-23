package com.xdc.basic.api.apache.commons.chain.framwork.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExecuteResultHandlerHolder
{
    private static final Map<String, ExecuteResultHandler> map      = new ConcurrentHashMap<String, ExecuteResultHandler>();
    private static final ExecuteResultHandlerHolder        instance = new ExecuteResultHandlerHolder();

    private ExecuteResultHandlerHolder()
    {
    }

    public static ExecuteResultHandlerHolder getInstance()
    {
        return instance;
    }

    public ExecuteResultHandler get(String requestId)
    {
        return map.get(requestId);
    }

    public void put(String requestId, ExecuteResultHandler executeResultHandler)
    {
        map.put(requestId, executeResultHandler);
    }

    public boolean containsRequsetId(String requstId)
    {
        return map.containsKey(requstId);
    }
}
