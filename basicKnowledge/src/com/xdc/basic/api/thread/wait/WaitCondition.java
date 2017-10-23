package com.xdc.basic.api.thread.wait;

public class WaitCondition
{
    /**
     * 开始等待
     */
    public void waitConditionStart()
    {
    }

    /**
     * 轮询条件，如果返回值为true，结束等待。如果返回值为false，则继续等待。
     */
    public boolean evalCondition()
    {
        return false;
    }

    /**
     * 轮询期间，可以在此做一下动作。
     */
    public void doActionPerInterval()
    {
    }

    /**
     * 等待超时，结束等待。
     */
    public void waitConditionTimeout()
    {
    }

    /**
     * 条件满足，结束等待。
     */
    public void waitConditionEnd()
    {
    }
}
