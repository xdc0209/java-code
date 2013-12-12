package com.xdc.basic.api.executor.asyncprocess;

public abstract class abstractProcess
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
