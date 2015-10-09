package com.xdc.basic.api.thread.wait;

public class WaitCondition
{
    /**
     * 如果返回值为false，则一直等待直到超时
     */
    public boolean evalCondition()
    {
        return false;
    }

    public void doActionPerInterval()
    {
    }

    /**
     * 超时之后的回调接口
     */
    public void waitConditionTimeout()
    {
    }
}