package com.xdc.basic.api.thread.uncaught.exception.handler;

import java.lang.Thread.UncaughtExceptionHandler;

public class UncaughtExceptionHandlerTest
{
    public static void main(String[] args)
    {
        System.out.println("Main start.");

        ErrorHandler handle = new ErrorHandler();
        Thread.setDefaultUncaughtExceptionHandler(handle); // 设置所有线程的UncaughtExceptionHandler

        MyThread t = new MyThread();
        // t.setUncaughtExceptionHandler(handle); // 设置单个线程的UncaughtExceptionHandler,这样可以实现不同的线程可以有不同的UncaughtExceptionHandler
        t.start();

        System.out.println("Main finish.");
    }
}

/**
 * 自定义的一个UncaughtExceptionHandler
 */
class ErrorHandler implements UncaughtExceptionHandler
{
    /**
     * 这里可以做任何针对异常的处理, 比如记录日志等等
     */
    @Override
    public void uncaughtException(Thread a, Throwable e)
    {
        System.out.println("UncaughtException: ThreadName=[" + a.getName() + "], Message:" + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * 拥有UncaughtExceptionHandler的线程
 */
class MyThread extends Thread
{
    @Override
    public void run()
    {
        double i = 12 / 0; // 抛出异常的地方
        System.out.println(i);
    }
}
