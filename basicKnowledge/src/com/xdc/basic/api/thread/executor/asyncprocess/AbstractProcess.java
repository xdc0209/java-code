package com.xdc.basic.api.thread.executor.asyncprocess;

public abstract class AbstractProcess
{
    public String asyncProcess(final String para)
    {
        ThreadExecutor.getInstance().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    process(para);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        return "success!";
    }

    abstract void process(String para);
}
