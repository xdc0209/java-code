package com.xdc.basic.api.thread.wait;

public class WaitUtils
{
    private final static long SLEEP_STEP = 500;

    /**
     * 等待条件、超时时间（毫秒）
     * 
     * @param condition
     * @param timeout
     */
    public static void waitConditionUtilTimeout(WaitCondition condition, long timeout)
    {
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

        if (!condition.evalCondition())
        {
            condition.waitConditionTimeout();
        }
    }

    //    public static class WaitCondition
    //    {
    //        /**
    //         * 如果返回值为false，则一直等待直到超时
    //         */
    //        public boolean evalCondition()
    //        {
    //            return false;
    //        }
    //
    //        public void doActionPerInterval()
    //        {
    //
    //        }
    //
    //        /**
    //         * 超时之后的回调接口
    //         */
    //        public void waitConditionTimeout()
    //        {
    //
    //        }
    //    }
}