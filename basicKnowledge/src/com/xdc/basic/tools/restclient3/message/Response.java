package com.xdc.basic.tools.restclient3.message;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Response
{
    private int    statusCode;
    private String bodyType;
    private String body;

    public Response()
    {
        super();
    }

    public Response(int statusCode, String bodyType, String body)
    {
        super();
        this.statusCode = statusCode;
        this.bodyType = bodyType;
        this.body = body;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getBodyType()
    {
        return bodyType;
    }

    public void setBodyType(String bodyType)
    {
        this.bodyType = bodyType;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}