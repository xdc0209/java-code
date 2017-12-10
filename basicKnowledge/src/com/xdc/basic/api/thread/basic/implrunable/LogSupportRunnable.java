package com.xdc.basic.api.thread.basic.implrunable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.log.slf4j.LogTest;

/**
 * 为了避免线程因异常而不留痕迹的消失，将业务代码都在try-catch执行。此类作为线程的基类，继承此类即可实现线程。
 */
public abstract class LogSupportRunnable implements Runnable
{
    private static Logger log = LoggerFactory.getLogger(LogTest.class);

    @Override
    public final void run()
    {
        try
        {
            doRun();
        }
        catch (Throwable e)
        {
            log.error("Error occured when running thread.", e);
        }
    }

    public abstract void doRun() throws Throwable;
}
