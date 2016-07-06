package com.xdc.basic.api.rmi.sbus.core;

import java.io.Serializable;

public class InvokeResult implements Serializable
{
    private static final long serialVersionUID = -377150813534937429L;

    // 消息id，用于消息跟踪。在调用信息（InvokeInfo）中取出，然后设置到调用结果（InvokeResult）中。
    private String            id;

    private Object            result;

    private Throwable         throwable;

    public InvokeResult()
    {
        super();
    }

    public InvokeResult(String id, Object result)
    {
        super();
        this.id = id;
        this.result = result;
    }

    public InvokeResult(String id, Throwable throwable)
    {
        super();
        this.id = id;
        this.throwable = throwable;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Object getResult()
    {
        return result;
    }

    public void setResult(Object result)
    {
        this.result = result;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }
}
