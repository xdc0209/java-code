package com.xdc.basic.api.thread.wait;

public class WaitUtils
{
    private final static long SLEEP_STEP = 1000;

    /**
     * 等待条件、超时时间（毫秒）
     * 
     * @param condition
     * @param timeout
     */
    public static void waitConditionUtilTimeout(WaitCondition condition, long timeout)
    {
        condition.waitConditionStart();

        long waittime = 0;
        while (!condition.evalCondition() && waittime < timeout)
        {
            try
            {
                Thread.sleep(SLEEP_STEP);
            }
            catch (InterruptedException e)
            {
                // do nothing
            }

            condition.doActionPerInterval();
            waittime += SLEEP_STEP;
        }

        if (condition.evalCondition())
        {
            condition.waitConditionEnd();
        }
        else
        {
            condition.waitConditionTimeout();
        }
    }
}
