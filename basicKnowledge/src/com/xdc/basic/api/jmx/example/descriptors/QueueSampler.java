/**
 * QueueSampler.java - MXBean implementation for the QueueSampler MXBean.
 * This class must implement all the Java methods declared in the
 * QueueSamplerMXBean interface, with the appropriate behavior for each one.
 */

package com.xdc.basic.api.jmx.example.descriptors;

import java.util.Date;
import java.util.Queue;

public class QueueSampler implements QueueSamplerMXBean
{
    private final Queue<String> queue;

    public QueueSampler(Queue<String> queue)
    {
        this.queue = queue;
    }

    @Override
    public QueueSample getQueueSample()
    {
        synchronized (queue)
        {
            return new QueueSample(new Date(), queue.size(), queue.peek());
        }
    }

    @Override
    public void clearQueue()
    {
        synchronized (queue)
        {
            queue.clear();
        }
    }
}
