package com.xdc.basic.api.thread.executor.threadfactory;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(ThreadUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        log.error(String.format("Caught an uncaught exception in thread %s.", t.getName()), e);
    }
}