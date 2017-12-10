package com.xdc.basic.api.thread.basic.implcallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Test
{
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new CallableImpl());
        String result = future.get();
        System.out.println(result);
    }
}
