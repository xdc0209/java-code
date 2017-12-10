package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.handler.HandlerContext;
import com.xdc.basic.api.apache.commons.chain.framwork.handler.HandlerFactory;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.RequestQueue;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.ResponseQueue;

public class RequestProcess extends MessageProcess
{
    private static Log            log      = LogFactory.getLog(RequestProcess.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(100, new RequestHandlerThreadFactory());

    @Override
    public void stop()
    {
        super.stop();
        executor.shutdown();
    }

    @Override
    public void handle()
    {
        final Request request = RequestQueue.getInstance().take();

        final HandlerContext handlerContext = new HandlerContext(request);

        List<Command> handers = HandlerFactory.getHanders(request.getClass());
        final Chain chain = new ChainBase(handers);

        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    chain.execute(handlerContext);

                    Response response = handlerContext.getResponse();
                    response.setRequestId(request.getRequestId());

                    ResponseQueue.getInstance().put(response);

                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        });

    }

    @Override
    public ThreadFactory getThreadFactory()
    {
        return new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                Thread t = new Thread(r);
                t.setName("RequestProcess-");
                return t;
            }
        };
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
