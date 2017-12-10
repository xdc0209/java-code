package com.xdc.basic.api.apache.commons.chain.framwork.message;

public class Response
{
    private String requestId;

    private long   code;

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public long getCode()
    {
        return code;
    }

    public void setCode(long code)
    {
        this.code = code;
    }
}
