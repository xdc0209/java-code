package com.xdc.soft.mini.consumer;

import com.xdc.soft.mini.compute.Compute;

public class Consumer
{
    private Compute compute;

    public Compute getCompute()
    {
        return compute;
    }

    public void setCompute(Compute computeOsgi)
    {
        this.compute = computeOsgi;
    }

    public void init()
    {
        RunnableImpl r = new RunnableImpl(this.compute);
        Thread t = new Thread(r);
        t.setName("miniConsole");
        t.start();
    }
}
