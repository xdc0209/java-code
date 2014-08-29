package com.xdc.basic.api.apache.commons.chain.framwork.message;

public class Response
{
    private String requestId;

    private int    code;

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}
