package com.xdc.basic.api.apache.commons.chain.framwork;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.RequestQueue;

public class RequestProcess extends AbstractMessageProcess
{
    private static Log            log      = LogFactory.getLog(RequestProcess.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(100, new RequestHandlerThreadFactory()); ;

    @Override
    public void stop()
    {
        super.stop();
        executor.shutdown();
    }

    @Override
    public void handle()
    {
        Request request = RequestQueue.getInstance().take();

        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                Chain chain = new ChainBase();
                Context ctx = new ContextBase();
                try
                {
                    chain.execute(ctx);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        });
    }
}

class RequestHandlerThreadFactory implements ThreadFactory
{
    private static final String THREAD_NAME_PREFIX = "RequestHandler-";

    private final AtomicLong    seq                = new AtomicLong(0);

    @Override
    public Thread newThread(Runnable r)
    {
        Thread t = new Thread(r);
        t.setName(THREAD_NAME_PREFIX + seq.getAndIncrement());
        return t;
    }
}