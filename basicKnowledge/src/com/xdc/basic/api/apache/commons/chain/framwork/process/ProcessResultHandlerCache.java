package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessResultHandlerCache
{
    private static final Map<String, ProcessResultHandler> map = new ConcurrentHashMap<String, ProcessResultHandler>();

    private ProcessResultHandlerCache()
    {
    }

    public static ProcessResultHandler get(String requestId)
    {
        return map.get(requestId);
    }

    public static void put(String requestId, ProcessResultHandler processResultHandler)
    {
        map.put(requestId, processResultHandler);
    }

    public static boolean staticcontainsRequsetId(String requstId)
    {
        return map.containsKey(requstId);
    }
}
