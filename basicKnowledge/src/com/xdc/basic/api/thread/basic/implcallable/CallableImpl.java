package com.xdc.basic.api.thread.basic.implcallable;

import java.util.concurrent.Callable;

public class CallableImpl implements Callable<String>
{
    @Override
    public String call() throws Exception
    {
        return "I'm in callable.";
    }
}
