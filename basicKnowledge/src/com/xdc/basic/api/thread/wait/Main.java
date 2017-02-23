package com.xdc.basic.api.thread.wait;

import java.util.Date;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Start:" + new Date());

        WaitUtils.waitConditionUtilTimeout(new WaitCondition()
        {
            /**
             * 骰子
             */
            long dice = -1;

            @Override
            public boolean evalCondition()
            {
                System.out.println("The dice is " + dice + ".");
                if (dice == 6)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            public void doActionPerInterval()
            {
                dice = System.currentTimeMillis() % 6 + 1;
            }

            @Override
            public void waitConditionTimeout()
            {
                throw new RuntimeException("Wait to [ get dice == 6 ] time out.");
            }

        }, 5000);

        System.out.println("End:  " + new Date());
    }
}
